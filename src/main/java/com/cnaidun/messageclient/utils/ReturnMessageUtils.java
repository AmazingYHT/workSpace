package com.cnaidun.messageclient.utils;

import com.alibaba.fastjson.JSONObject;

public class ReturnMessageUtils {

    public static String code = "code";
    public static String msg = "msg";
    public static String data = "data";

    public static String SUCCESS_200 = "200";
    public static String SUCCESS_200_NAME = "success";

    public static String ERROR_1102 = "1102";
    public static String ERROR_1102_NAME = "系统错误";

    public static String ERROR_1104 = "1104";
    public static String ERROR_1104_NAME = "系统超时";

    /**
     * 返回信息封装
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JSONObject returnMsgObject(String code,String msg,Object data){
        JSONObject returnObject = new JSONObject();
        returnObject.put(ReturnMessageUtils.code,code);
        returnObject.put(ReturnMessageUtils.msg,msg);
        returnObject.put(ReturnMessageUtils.data,data);
        return returnObject;
    }

    /**
     * 系统错误
     * @param data
     * @return
     */
    public static JSONObject returnError1102Object(String data){
        JSONObject returnObject = new JSONObject();
        returnObject.put(ReturnMessageUtils.code,ReturnMessageUtils.ERROR_1102);
        returnObject.put(ReturnMessageUtils.msg,ReturnMessageUtils.ERROR_1102_NAME);
        returnObject.put(ReturnMessageUtils.data,data);
        return returnObject;
    }

    /**
     * 返回正确信息
     * @return
     */
    public static JSONObject returnSuccessObject(Object data){
        JSONObject returnObject = new JSONObject();
        returnObject.put(ReturnMessageUtils.code,ReturnMessageUtils.SUCCESS_200);
        returnObject.put(ReturnMessageUtils.msg,ReturnMessageUtils.SUCCESS_200_NAME);
        returnObject.put(ReturnMessageUtils.data,data);
        return returnObject;
    }

}
