package com.cnaidun.messageclient.service;

import com.alibaba.fastjson.JSONObject;
import com.cnaidun.messageclient.MQServer;
import com.cnaidun.messageclient.config.MessageClientConfig;
import com.cnaidun.messageclient.service.config.ConsumerHandler;
import com.cnaidun.messageclient.service.listener.MessageReceiveListener;
import com.cnaidun.messageclient.utils.MessageContextUtils;
import com.cnaidun.messageclient.utils.MessageParameterStatic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConsumerHandlerImpl implements ConsumerHandler {

    @Autowired
    private MQServer mqServer;

    @Autowired
    private MessageClientConfig messageClientConfig;

    //1.1 首先定义一个私有的、空的监听器对象
    private MessageReceiveListener messageReceiveListener;

    public static ConcurrentHashMap<String, String> needBackMessageMap = new ConcurrentHashMap<String, String>();

    public ConsumerHandlerImpl() {
    }

    //1.2 将传递进来的事件监听器付给1.1中的listener
    public void registerListener(MessageReceiveListener messageReceiveListener) {
        this.messageReceiveListener = messageReceiveListener;
    }

    public ConsumerHandlerImpl(MessageReceiveListener messageReceiveListener) {
        this.messageReceiveListener = messageReceiveListener;
    }

    @Override
    public void handleMessage(JSONObject jsonMsg) {
        // 可修改部分
        //  发送消息 还是 接收消息
        int messageType = MessageParameterStatic.MESSAGETYPE_SEND;
        if (jsonMsg.containsKey(MessageParameterStatic.messageType)) {
            messageType = jsonMsg.getInteger(MessageParameterStatic.messageType);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.messageType);
        }
        //  发送MQ服务名
        String producer = null;
        if (jsonMsg.containsKey(MessageParameterStatic.producer)) {
            producer = jsonMsg.getString(MessageParameterStatic.producer);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.producer);
        }
        // 是否需要返回
        boolean backFlag = false;
        if (jsonMsg.containsKey(MessageParameterStatic.backFlag)) {
            backFlag = jsonMsg.getBoolean(MessageParameterStatic.backFlag);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.backFlag);
        }

        // 不可修改部分
        // 发送渠道：MQ 或 eureka
        int channelType = MessageParameterStatic.CHANNELTYPE_MQ;
        if (jsonMsg.containsKey(MessageParameterStatic.channelType)) {
            channelType = jsonMsg.getInteger(MessageParameterStatic.channelType);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.channelType);
        }
        // 消息创建时间
        long createtime = System.currentTimeMillis();
        if (jsonMsg.containsKey(MessageParameterStatic.createtime)) {
            createtime = jsonMsg.getLong(MessageParameterStatic.createtime);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.createtime);
        }
        // 组织名
        String organization = null;
        if (jsonMsg.containsKey(MessageParameterStatic.organization)) {
            organization = jsonMsg.getString(MessageParameterStatic.organization);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.organization);
        }
        // 分组名
        String group = null;
        if (jsonMsg.containsKey(MessageParameterStatic.group)) {
            group = jsonMsg.getString(MessageParameterStatic.group);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.group);
        }
        // 业务类型
        String businessType = null;
        if (jsonMsg.containsKey(MessageParameterStatic.businessType)) {
            businessType = jsonMsg.getString(MessageParameterStatic.businessType);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.businessType);
        }
        // 业务码
        String businessCode = null;
        if (jsonMsg.containsKey(MessageParameterStatic.businessCode)) {
            businessCode = jsonMsg.getString(MessageParameterStatic.businessCode);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.businessCode);
        }
        // 消息体
        String msg = null;
        if (jsonMsg.containsKey(MessageParameterStatic.msg)) {
            msg = jsonMsg.getString(MessageParameterStatic.msg);
            msg = (msg == null ? "" : msg);
        } else {
            log.error("** can not get jsonMsg's key = " + MessageParameterStatic.msg);
        }

        //此消息为返回的消息
        if (messageType == MessageParameterStatic.MESSAGETYPE_BACK) {
            // 定义为mq的返回Key
            String strKey = channelType + "." + organization + "." + group + "." + businessType + "." + businessCode + "." + createtime;
            needBackMessageMap.put(strKey, msg);
            System.out.println("input key  = " + strKey + "      needBackMessageMap.size() = " + needBackMessageMap.size());
            return;
        }

        if (this.messageReceiveListener != null) {
            if (backFlag) {
                // 直接返回给的消息接收监听
                String receiveMsg = this.messageReceiveListener.receiveAndSend(msg);

                JSONObject returnMsg = new JSONObject();
                returnMsg.put(MessageParameterStatic.messageType, MessageParameterStatic.MESSAGETYPE_BACK);
                if (null == messageClientConfig)
                    messageClientConfig = MessageContextUtils.getBean(MessageClientConfig.class);

                returnMsg.put(MessageParameterStatic.producer, messageClientConfig.getMqname());
                returnMsg.put(MessageParameterStatic.backFlag, false);

                returnMsg.put(MessageParameterStatic.channelType, channelType);
                returnMsg.put(MessageParameterStatic.createtime, createtime);
                returnMsg.put(MessageParameterStatic.organization, organization);
                returnMsg.put(MessageParameterStatic.group, group);
                returnMsg.put(MessageParameterStatic.businessType, businessType);
                returnMsg.put(MessageParameterStatic.businessCode, businessCode);
                returnMsg.put(MessageParameterStatic.msg, receiveMsg);
                if (null == mqServer)
                    mqServer = MessageContextUtils.getBean(MQServer.class);
                log.debug("** DataService send msg = " + returnMsg.toJSONString());
                mqServer.getProducerConfiguration().send(returnMsg);
            } else {
                this.messageReceiveListener.receive(msg);
            }
        }
    }
}
