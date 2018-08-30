package com.cnaidun.user.api.scheduleApproveTask.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnaidun.user.api.scheduleApproveTask.bean.*;
import com.cnaidun.user.api.scheduleApproveTask.common.utils.MyX509TrustManager;
import com.cnaidun.user.api.scheduleApproveTask.service.WeiXindataInterChangeService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.cnaidun.messageclient.MQServer;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;

@Component
@Configurable
@EnableScheduling
public class WxScheduleTask {


     @Value("${wx.appId}")
    private  String APPID;
            //="wxec480457924d9fd8";

     @Value("${wx.appSecret}")
    private  String APPSECRET;
             //="b1ff6d0a8f79f4cf03a4fefbf5116bd1";

    @Value("${wx.tempId}")
    private  String regTempId;

    @Autowired
    private WeiXindataInterChangeService weiXindataInterChangeService;


    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private static final   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(WxScheduleTask.class);

    private static Map<String,Object> mapToken = new HashMap<String,Object>();





    //每天6：30执行一次
    //   @Scheduled(cron = "30 30 6 * * ?")
    @Scheduled(cron = "0 */1 *  * * * ")
    public void sendWx() {
        List<WeiXinDataInterChange> list = weiXindataInterChangeService.selectUserInfo();

        if(list.size()==0){
            logger.error("数据为空");
            return;
        }

        List<WorkType>  workList = weiXindataInterChangeService.selectAll();

        List<IdInfo>  idList = weiXindataInterChangeService.selectIdInfo();

        Map<String,IdInfo>  map = new HashMap<String,IdInfo>();

        Map<String,String>  mapType = new HashMap<String,String>();

        for(WorkType workType:workList){
            mapType.put(workType.getWorktypecode(),workType.getWorkname());
        }

        for(IdInfo idInfo:idList){
             map.put(idInfo.getUid(),idInfo);
        }

        for(WeiXinDataInterChange wxSend:list){
           String uid = wxSend.getUid();
           if(map.containsKey(uid)){
               IdInfo id = map.get(uid);
               wxSend.setName(id.getName());
               wxSend.setOpenid(id.getOpenid());
           }

           String workTypeCode = wxSend.getWorktypecode();
           if(mapType.containsKey(workTypeCode)){
               wxSend.setWorkname(mapType.get(workTypeCode));
           }
        }
        for(WeiXinDataInterChange data:list){
            logger.info("处理数据为"+data);
            if(!StringUtils.isEmpty(data.getOpenid())){

                dealWxOpenId(data);
            }
        }

    }

    private  String dealWxOpenId(WeiXinDataInterChange data) {
        String openId = data.getOpenid();
        Date expiryDate = (Date) mapToken.get("expireDate");
        if(expiryDate==null){
            expiryDate = new Date();
        }

        Date now = new Date();

        long interval = (now.getTime() - expiryDate.getTime())/1000;
        String token= "";

        if(mapToken.get("token")!=null&&interval<7000){
            token = (String) mapToken.get("token");
            logger.info("从内存中获取"+token);

        }else{
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
            JSONObject jsonTokenObject = null;
            try {
                jsonTokenObject = doGetJson(tokenUrl);
                logger.info("用户信息token为"+jsonTokenObject );

                token=jsonTokenObject.getString("access_token");
                mapToken.put("token",token);
                mapToken.put("expireDate",now);

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
//        Integer subscribe = null;
//        String tmpurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token +"&openid="+openId;
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = doGetJson(tmpurl);
//            logger.info("用户信息token为"+jsonObject );
//            String errmsg = (String) jsonObject.get("errmsg");
//            if(errmsg==null){
//                //用户是否订阅该公众号标识（0代表此用户没有关注该公众号 1表示关注了该公众号）。
//                subscribe = (Integer) jsonObject.get("subscribe");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        if(subscribe==1){
            Map<String,String> param = new HashMap<>();
            //注册的微信-模板Id
            String regTempId =  "U9DEbabIFRNHmknDgAm-kFvGUm4Dje8buOUZSmf6W4o";

            //调用发送微信消息给用户的接口
            sendWechatMsgToUser(openId,regTempId,  "#000000",data);
  //      }

        return "";
    }

    /**
     * 发送微信消息(模板消息)
     * @param touser 用户 OpenID
     * @param templatId 模板消息ID
     * @param topcolor 标题颜色
     * @return
     */
    public  String sendWechatMsgToUser(String touser, String templatId,String topcolor,WeiXinDataInterChange data) {
        Date dateTime = data.getCreatetime();
        String blTime = sdf.format(dateTime);

        String status = data.getStatus();

        String dealStatus = dealStatus(status);

        JSONArray send = new JSONArray();

        Date expiryDate = (Date) mapToken.get("expireDate");
        if(expiryDate==null){
            expiryDate = new Date();
        }

        Date now = new Date();

        long interval = (now.getTime() - expiryDate.getTime())/1000;
        String token= "";

        if(mapToken.get("token")!=null&&interval<7000){
            token = (String) mapToken.get("token");
            logger.info("从内存获取token" + token);

        }else{
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
            JSONObject jsonTokenObject = null;
            try {
                jsonTokenObject = doGetJson(tokenUrl);
                logger.info("用户信息token为"+jsonTokenObject );

                token=jsonTokenObject.getString("access_token");
                mapToken.put("token",token);
                mapToken.put("expireDate",now);

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        WxTemplate t = new WxTemplate();
        t.setUrl("");
        t.setTouser(touser);
        t.setTopcolor("#000000");
        t.setTemplate_id(templatId);
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData first = new TemplateData();
        first.setColor("#000000");
        first.setValue("尊敬的"+data.getName()+"您好");
        m.put("first", first);
//        TemplateData name = new TemplateData();
//        name.setColor("#000000");
//        name.setValue(data.getWorkno());
//        m.put("keyword1", name);

        TemplateData name1 = new TemplateData();
        name1.setColor("#000000");
        name1.setValue(data.getWorkname());
        m.put("keyword1", name1);

        TemplateData name2 = new TemplateData();
        name2.setColor("#000000");
        name2.setValue(dealStatus);
        m.put("keyword2", name2);

        TemplateData name3 = new TemplateData();
        name3.setColor("#000000");
        name3.setValue(blTime);
        m.put("keyword3", name3);

//        TemplateData name4 = new TemplateData();
//        name4.setColor("#000000");
//        name4.setValue("重庆市渝中区国家税务局税源管理三科");
//        m.put("keyword5", name4);

        TemplateData remark = new TemplateData();
        remark.setColor("#000000");
        remark.setValue(data.getOpinion());
        m.put("remark", remark);
        t.setData(m);

        String json =  JSONObject.toJSONString(t);

        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ token;

        try{
            JSONObject result = httpsRequest(tmpurl, "POST",json);

            //   JSONObject resultJson = JSONObject.parseObject(result);
            logger.info("发送微信消息返回信息：" + result.get("errcode"));
            String errmsg = (String) result.get("errmsg");
            if("ok".equals(errmsg)) {
                logger.info("发送成功");
                weiXindataInterChangeService.updateStatus(data);
            }else{
                String errcode = (String) result.get("errcode");
                logger.info("错误码"+errcode);

                if("40001".equals(errcode)){
                    String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
                    JSONObject jsonTokenObject = null;
                    try {
                        jsonTokenObject = doGetJson(tokenUrl);
                        logger.info("用户信息token为"+jsonTokenObject );

                        token=jsonTokenObject.getString("access_token");
                        mapToken.put("token",token);
                        mapToken.put("expireDate",now);
                        sendWechatMsgToUser( touser, templatId, topcolor, data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            }catch(Exception e){
            e.printStackTrace();
            return "error";
        }finally {

        }
        return "success";
    }

    private String dealStatus(String status) {
        String info ="";

        if("01".equals(status)){
             info ="待签收";
        }else if ("02".equals(status)){
            info ="待审核";

        }else if ("03".equals(status)){
            info ="补充材料";

        }else if ("04".equals(status)){
            info ="不予受理";

        }else if ("05".equals(status)){
            info ="符合当场办理";

        }else if ("06".equals(status)){
            info ="进入审批流程";

        }else if ("07".equals(status)){
            info ="审批通过同意办理";

        }else if ("08".equals(status)){
            info ="已办理";

        }else if ("09".equals(status)){
            info ="不予办理";

        }else if ("91".equals(status)){
            info ="核验中止办理";

        }else if ("92".equals(status)){
            info ="核验通过办理";

        }
        return info;
    }


    /**
     * 封装模板详细信息
     * @return
     */
    public static JSONObject packJsonmsg(Map<String, String> param) {
        JSONObject json = new JSONObject();
        for (Map.Entry<String,String> entry : param.entrySet()) {
            JSONObject keyJson = new JSONObject();
            String  data=  entry.getValue();

            json.put(entry.getKey(), data);
        }
        return json;
    }

    /**
     * 根据模板的编号 新增并获取模板ID
     * @param
     * @return 模板ID
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return jsonObject;

    }

    public  static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject=null;
        DefaultHttpClient defaultHttpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
        HttpEntity httpEntity=httpResponse.getEntity();
        if(httpEntity!=null){
            String result= EntityUtils.toString(httpEntity,"UTF-8");
            jsonObject=jsonObject.parseObject(result);
            //System.out.println("jsonObject:  "+jsonObject);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    public static void main(String args[]){
      //  new WxScheduleTask().dealWxOpenId("oWbIB1XGaDAIDwQ1uNmW9gQt3TI4");
    }

}
