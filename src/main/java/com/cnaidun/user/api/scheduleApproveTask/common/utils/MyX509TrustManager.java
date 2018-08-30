package com.cnaidun.user.api.scheduleApproveTask.common.utils;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 微信请求 - 信任管理器
 */
public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        //        return new X509Certificate[0];
        return  null;
    }
}