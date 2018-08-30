package com.cnaidun.messageclient.utils;

public class MessageParameterStatic {

    // 封装信息时“是否需要返回信息”的标识属性
    public static String messageType = "messageType";
    public static String backFlag = "backFlag";
    public static String producer = "producer";

    public static String channelType = "channelType";
    public static String createtime = "createtime";
    public static String organization = "organization";
    public static String group= "group";
    public static String businessType= "businessType";
    public static String businessCode= "businessCode";
    public static String msg= "msg";


    public static int MESSAGETYPE_SEND = 1 ;    //发送的消息
    public static int MESSAGETYPE_BACK = 0;     //返回的消息

    // 使用消息通道的方式：EUREKA，MQ
    public static int CHANNELTYPE_MQ = 1;
    public static int CHANNELTYPE_EUREKA = 0;

}
