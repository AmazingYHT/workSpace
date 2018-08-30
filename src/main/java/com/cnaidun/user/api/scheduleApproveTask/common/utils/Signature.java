package com.cnaidun.user.api.scheduleApproveTask.common.utils;

/**
 * @author : turbo
 * @version : 1.0
 * @created : 2018/06/29
 * @description :
 */

import org.apache.commons.codec.binary.Base64;

/**
 * signature鉴权签名
 * 		内部包含了使用在拉取证件库或实名核身接口两个版本
 * @author DerrickZheng
 * @version 1.0
 */
public class Signature {

    /**
     * signature	鉴权签名   实名核身接口使用
     * @param secretKey
     * @param plainText
     * 		a=xxxxx&m=xxxxxxx&t=1427786065&e=600
     *			a 为appid
     *			m 为调用的api 名，
     *			t 为当前时间戳，是一个符合UNIX Epoch 时间戳规范的数值，单位为秒
     *			e 为此签名的凭证有效期，是一个符合UNIX Epoch 时间戳规范的数值，单位为秒
     * @author DerrickZheng
     */
    public static String getSignature(String secretKey, String plainText)
            throws Exception{
//        System.out.println("plainText:"+plainText);
        byte[] bin = Sha1.getHashHmac_Sha1(plainText, secretKey);
        byte[] all = new byte[bin.length + plainText.getBytes().length];
        System.arraycopy(bin, 0, all, 0, bin.length);
        System.arraycopy(plainText.getBytes(), 0, all, bin.length
                , plainText.getBytes().length);
        return new String(Base64.encodeBase64(all));
    }
}