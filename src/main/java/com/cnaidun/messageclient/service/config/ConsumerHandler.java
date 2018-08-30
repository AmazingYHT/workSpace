package com.cnaidun.messageclient.service.config;

import com.alibaba.fastjson.JSONObject;

public interface ConsumerHandler {
    public void handleMessage(JSONObject jsonObject);
}
