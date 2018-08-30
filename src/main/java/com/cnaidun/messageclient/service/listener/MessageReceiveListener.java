package com.cnaidun.messageclient.service.listener;

/**
 * 事件监听器
 */
public interface MessageReceiveListener {

    public void receive(String msg);

    public String receiveAndSend(String msg);
}
