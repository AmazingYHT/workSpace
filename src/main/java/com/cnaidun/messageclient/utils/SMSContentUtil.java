package com.cnaidun.messageclient.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SMSContentUtil {
    public static final int APPID = 1400114381;
    public static final String APPKEY = "47198744f79472a26834469fc7d5c070";
    public static final int TTEMPLATEID = 157978;//普通短信模版
    public static final int TTEMPLATEID_Z = 170296;//注册短信模版
    public static final String SMSSIGN = "";

    //stringMD5(APPKEY.concat(phone))
    private static String stringMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] inputByteArray = input.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
