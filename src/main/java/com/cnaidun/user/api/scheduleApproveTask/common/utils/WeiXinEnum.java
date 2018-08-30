package com.cnaidun.user.api.scheduleApproveTask.common.utils;

public class WeiXinEnum {

 // 模板消息编号
 public enum WX_TEMPLATE_MSG_NUMBER {

     USER_SMS_SUCCESS(0, "TM00015", "短信发送成功");
     private int code;
     private String msgNumber;
     private String label;

     WX_TEMPLATE_MSG_NUMBER(int code, String msgNumber, String label) {
         this.code = code;
         this.msgNumber = msgNumber;
         this.label = label;
     }

     public int getCode() {
         return code;
     }

     public String getMsgNumber() {
         return msgNumber;
     }

     public String getLabel() {
         return label;
     }
     }
 }
