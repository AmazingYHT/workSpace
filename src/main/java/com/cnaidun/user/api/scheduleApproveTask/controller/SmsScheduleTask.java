package com.cnaidun.user.api.scheduleApproveTask.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cnaidun.messageclient.MQServer;
import com.cnaidun.messageclient.utils.ReturnMessageUtils;
import com.cnaidun.messageclient.utils.SMSService;
import com.cnaidun.user.api.scheduleApproveTask.bean.IdInfo;
import com.cnaidun.user.api.scheduleApproveTask.bean.WeiXinDataInterChange;
import com.cnaidun.user.api.scheduleApproveTask.bean.WorkType;
import com.cnaidun.user.api.scheduleApproveTask.common.utils.StringUtils;
import com.cnaidun.user.api.scheduleApproveTask.service.SmsdataInterChangeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configurable
@EnableScheduling
@Slf4j
public class SmsScheduleTask {

    // @Autowired
    //private MQServer mqServer;

    @Autowired
    private SmsdataInterChangeService smsdataInterChangeService;

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(SmsScheduleTask.class);

    private static Map<String, Object> mapToken = new HashMap<String, Object>();


    //每天6：30执行一次
    //@Scheduled(cron = "0 */1 *  * * * ")
    public void sendSms() {

        List<WeiXinDataInterChange> list = smsdataInterChangeService.selectUserInfo();

        List<WorkType> workList = smsdataInterChangeService.selectAll();

        List<IdInfo> idList = smsdataInterChangeService.selectIdInfo();

        Map<String, IdInfo> map = new HashMap<String, IdInfo>();

        Map<String, String> mapType = new HashMap<String, String>();

        for (WorkType workType : workList) {
            mapType.put(workType.getWorktypecode(), workType.getWorkname());
        }

        for (IdInfo idInfo : idList) {
            map.put(idInfo.getUid(), idInfo);
        }

        for (WeiXinDataInterChange wxSend : list) {
            String uid = wxSend.getUid();
            if (map.containsKey(uid)) {
                IdInfo id;
                id = map.get(uid);
                wxSend.setName(id.getName());
                wxSend.setOpenid(id.getOpenid());
                wxSend.setPhone(id.getPhone());
            }

            String workTypeCode = wxSend.getWorktypecode();
            if (mapType.containsKey(workTypeCode)) {
                wxSend.setWorkname(mapType.get(workTypeCode));
            }
        }

        for (WeiXinDataInterChange data : list) {
            if (!StringUtils.isEmpty(data.getOpenid())) {
                dealWxOpenId(data);
            }
        }
    }

    private void dealWxOpenId(WeiXinDataInterChange data) {
        String dealStatus = dealStatus(data.getStatus());
        String templateId = smsTemplate(data.getStatus());
        if (!StringUtils.isEmpty(dealStatus) && !StringUtils.isEmpty(templateId)) {
            JSONObject msgParam = new JSONObject();
            JSONArray phoneArray = new JSONArray();
            JSONArray paramArray = new JSONArray();
            phoneArray.add(data.getPhone());//手机号码
            msgParam.put("phone", phoneArray);
            msgParam.put("param", paramArray);
            msgParam.put("templateId", "179416");
            msgParam.put("workTypeCode", "SMS0004");
//            String rzeceive = mqServer.sendAndReceive(
//                    "CNAIDUN",
//                    "BACKBISINESSGROUP",
//                    "SMS",
//                    "SMS0004",
//                    msgParam.toJSONString()
//            );

            String rzeceive = this.receiveAndSend(msgParam.toJSONString());
            logger.info("=====返回" + rzeceive);
            JSONObject jsonObject = JSONObject.parseObject(rzeceive);
            String code = jsonObject.getString("code");
            if (!StringUtils.isEmpty(rzeceive) && "200".equals(code)) {
                smsdataInterChangeService.updateStatus(data);
            }
        }
    }

    private String dealStatus(String status) {
        String info = "";
        if ("01".equals(status)) {
            info = "待签收";
        } else if ("02".equals(status)) {
            info = "待审核";
        } else if ("03".equals(status)) {
            info = "补充材料";
        } else if ("04".equals(status)) {
            info = "不予受理";
        } else if ("05".equals(status)) {
            info = "符合当场办理";
        } else if ("06".equals(status)) {
            info = "进入审批流程";
        } else if ("07".equals(status)) {
            info = "审批通过同意办理";
        } else if ("08".equals(status)) {
            info = "已办理";
        } else if ("09".equals(status)) {
            info = "不予办理";
        } else if ("91".equals(status)) {
            info = "核验中止办理";
        } else if ("92".equals(status)) {
            info = "核验通过办理";
        }
        return info;
    }

    private String smsTemplate(String status) {
        String info = "";
        if ("01".equals(status)) {//待签收
            info = "179416";
        } else if ("02".equals(status)) {//待审核
            info = "179417";
        } else if ("03".equals(status)) {//补充材料
            info = "179430";
        } else if ("04".equals(status)) {
            info = "179424";
        } else if ("05".equals(status)) {
            info = "179426";
        } else if ("06".equals(status)) {//进入审批流程
            info = "179428";
        } else if ("07".equals(status)) {
            info = "179420";
        } else if ("08".equals(status)) {//已经办理
            info = "179429";
        } else if ("09".equals(status)) {//不予办理
            info = "179422";
        } else if ("91".equals(status)) {//核验中止办理
            info = "179420";
        } else if ("92".equals(status)) {
            info = "179426";
        }
        return info;
    }

    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null) {
            String result;
            result = EntityUtils.toString(httpEntity, "UTF-8");
            jsonObject = jsonObject.parseObject(result);
            //System.out.println("jsonObject:  "+jsonObject);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    private String receiveAndSend(String msg) {
        //log.info("** DataService receive msg = "+msg);
        Map mapType = JSON.parseObject(msg, Map.class);
        Map map = new HashMap();
        for (Object obj : mapType.keySet()) {
            logger.info("key为：" + obj + "值为：" + mapType.get(obj));
            map.put(obj, mapType.get(obj));
        }
        if (map.get("workTypeCode") != null && map.get("workTypeCode").equals("SMS0003")) {
            if (map.get("phone") == null || map.get("code") == null) {
                logger.info("phone:" + map.get("phone") + "   code:" + map.get("code"));
                ReturnMessageUtils.returnSuccessObject("40035").toJSONString();
            }
            String[] params = {(String) map.get("code"), "5"};
            String result = SMSService.sendMeModel((String) map.get("phone"), params);
            if (!result.equals("OK")) {
                ReturnMessageUtils.returnSuccessObject(result).toJSONString();
            }
        } else if (map.get("workTypeCode") != null && map.get("workTypeCode").equals("SMS0004")) {
            if (map.get("phone") == null) {
                ReturnMessageUtils.returnSuccessObject("40035").toJSONString();
            }
            if (map.get("param") == null) {
                ReturnMessageUtils.returnSuccessObject("40035").toJSONString();
            }
            if (map.get("templateId") == null) {
                ReturnMessageUtils.returnSuccessObject("40035").toJSONString();
            }
            JSONArray arrayPhone = (JSONArray) map.get("phone");
            String[] phones = null;
            if (arrayPhone.size() > 0) {
                phones = new String[arrayPhone.size()];
                for (int i = 0; i < arrayPhone.size(); i++) {
                    phones[i] = (String) arrayPhone.get(i);
                }
            } else {
                phones = new String[0];
            }

            JSONArray arrayParam = (JSONArray) map.get("param");
            String[] params = null;
            if (arrayParam.size() > 0) {
                params = new String[arrayParam.size()];
                for (int i = 0; i < arrayParam.size(); i++) {
                    params[i] = (String) arrayParam.get(i);
                }
            } else {
                params = new String[0];
            }
            int templateid = Integer.parseInt((String) map.get("templateId"));
            String result = SMSService.sendMesModels(phones, params, templateid);
            if (!result.equals("OK")) {
                logger.info(result);
                ReturnMessageUtils.returnSuccessObject(result).toJSONString();
            }
        }
        return ReturnMessageUtils.returnSuccessObject("200").toJSONString();
    }
}