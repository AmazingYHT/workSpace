package com.cnaidun.messageclient.utils;


import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

/**
 * 短信服务类
 * 2018-05-16
 */
public class SMSService {


    /**
     * 自定义短信内容发送
     *
     * @param msg    短信内容
     * @param number 用户手机号
     * @return OK 成功  null 失败
     */
    public static String sendMess(String msg, String number) {
        try {
            SmsSingleSender ssender = new SmsSingleSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsSingleSenderResult result = ssender.send(0, "86", number,
                    msg, "", "");
            // System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定模板ＩＤ发送短信
     *
     * @param number 用户手机号
     * @param params 参数
     * @return OK 成功  null 失败
     */
    public static String sendMeModel(String number, String[] params) {
        try {
            // String[] params = {"hello" , "1" };//{参数}
            SmsSingleSender ssender = new SmsSingleSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", number,
                    SMSContentUtil.TTEMPLATEID_Z, params, SMSContentUtil.SMSSIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            // System.out.print(result);
            return result.errMsg;//OK
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 群发自定义短信
     *
     * @param msg     短信内容
     * @param numbers 用户手机号数组
     * @return OK 成功 null 失败
     */
    public static String sendMesModel(String msg, String[] numbers) {
        try {
            SmsMultiSender msender = new SmsMultiSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsMultiSenderResult result = msender.send(0, "86", numbers,
                    msg, "", "");
            //  System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定模板ID群发
     *
     * @param numbers 用户手机号数组
     * @param params  参数
     * @return OK 成功 null 失败
     */
    public static String sendMesModel(String[] numbers, String[] params) {
        try {
            //String[] params = {"hello" , "1" };//领取日期，到期日期
            SmsMultiSender msender = new SmsMultiSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsMultiSenderResult result = msender.sendWithParam("86", numbers,
                    SMSContentUtil.TTEMPLATEID, params, SMSContentUtil.SMSSIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            //   System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送语音消息
     *
     * @param number 用户手机号
     * @param msg    语音消息内容
     * @return OK 成功 null 失败
     */
    public static String sendMesVoice(String msg, String number) {
        try {
            SmsVoiceVerifyCodeSender vvcsender = new SmsVoiceVerifyCodeSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsVoiceVerifyCodeSenderResult result = vvcsender.send("86", number,
                    msg, 2, "");
            //   System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定模板ID群发
     *
     * @param numbers 用户手机号数组
     * @param params  参数
     * @return OK 成功 null 失败
     */
    public static String sendMesModels(String[] numbers, String[] params, int templateid) {
        try {
            //String[] params = {"hello" , "1" };//领取日期，到期日期
            SmsMultiSender msender = new SmsMultiSender(SMSContentUtil.APPID, SMSContentUtil.APPKEY);
            SmsMultiSenderResult result = msender.sendWithParam("86", numbers,
                    templateid, params, SMSContentUtil.SMSSIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            //   System.out.print(result);
            return result.errMsg;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return null;
    }
}