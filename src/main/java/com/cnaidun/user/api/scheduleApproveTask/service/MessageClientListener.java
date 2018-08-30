package com.cnaidun.user.api.scheduleApproveTask.service;

import com.cnaidun.messageclient.service.listener.MessageReceiveListener;

public class MessageClientListener implements MessageReceiveListener {
    @Override
    public void receive(String msg) {

    }

    @Override
    public String receiveAndSend(String msg) {
        return null;
    }
}
