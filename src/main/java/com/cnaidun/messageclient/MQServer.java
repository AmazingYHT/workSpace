package com.cnaidun.messageclient;

import com.alibaba.fastjson.JSONObject;
import com.cnaidun.messageclient.config.MessageClientConfig;
import com.cnaidun.messageclient.service.ConsumerHandlerImpl;
import com.cnaidun.messageclient.service.config.ConsumerConfig;
import com.cnaidun.messageclient.service.config.ProducerConfiguration;
import com.cnaidun.messageclient.service.listener.MessageReceiveListener;
import com.cnaidun.messageclient.utils.MessageParameterStatic;
import com.cnaidun.messageclient.utils.ReturnMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MQServer {

    @Autowired
    private MessageClientConfig messageClientConfig;

    // MQ消息发送机
    private static ProducerConfiguration producerConfiguration;
    // MQ消息接收机
    private static ConsumerConfig consumer;

    public void registerListener(MessageReceiveListener messageReceiveListener) {
        log.info("** MessageClientConfig : mqname=" + messageClientConfig.getMqname() + ", consumerNumber=" + messageClientConfig.getConsumerNumber() + ", messageChannel=" + messageClientConfig.getChannelType());

        String backExchange = messageClientConfig.getMqname() + "_BACK";
        String backQueeu = backExchange;
        String backRoutingKey = backExchange;
        if (null == producerConfiguration)
            producerConfiguration = new ProducerConfiguration(backExchange, backQueeu, backRoutingKey);

        log.info("** mqname = " + messageClientConfig.getMqname() + " producerConfiguration is ok");
        log.info("** backExchange = " + backExchange + ", backQueeu = " + backQueeu + ", backRoutingKey = " + backRoutingKey);

        String queue = messageClientConfig.getMqname();
        try {
            if (null == consumer) {
                consumer = new ConsumerConfig(queue, messageClientConfig.getConsumerNumber());
                consumer.registerListener(messageReceiveListener);
            }

            log.info("** mqname = " + messageClientConfig.getMqname() + " consumerConfig is ok");
            log.info("** queue = " + queue + ", consumerNumber = " + messageClientConfig.getConsumerNumber());
        } catch (Exception e) {
            log.error("** mqname = " + messageClientConfig.getMqname() + " consumerConfig is error");
            log.error("** queue = " + queue + ", consumerNumber = " + messageClientConfig.getConsumerNumber());
            log.error(e.getMessage());
        }
    }

    /**
     *
     * @param messageType
     *         MessageParameterStatic.MESSAGETYPE_SEND 发送的消息，
     *         MessageParameterStatic.MESSAGETYPE_BACK 返回的消息
     * @param backFlag 发送的消息是否需要返回
     * @param channelType
     * @param createtime
     * @param organization
     * @param group
     * @param businessType
     * @param businessCode
     * @param msg
     */
    private void producerSend(int messageType, boolean backFlag, int channelType, long createtime, String organization, String group, String businessType, String businessCode, String msg) {
        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put(MessageParameterStatic.messageType, messageType);
        jsonMsg.put(MessageParameterStatic.backFlag, backFlag);
        jsonMsg.put(MessageParameterStatic.producer, messageClientConfig.getMqname());

        jsonMsg.put(MessageParameterStatic.channelType, channelType);
        jsonMsg.put(MessageParameterStatic.createtime, createtime);
        jsonMsg.put(MessageParameterStatic.organization, organization);
        jsonMsg.put(MessageParameterStatic.group, group);
        jsonMsg.put(MessageParameterStatic.businessType, businessType);
        jsonMsg.put(MessageParameterStatic.businessCode, businessCode);
        jsonMsg.put(MessageParameterStatic.msg, msg);
        producerConfiguration.send(jsonMsg);
    }

    /**
     * 不需要返回信息的消息发送
     *
     * @param organization  组织编码
     * @param group         小组编码
     * @param businessType  业务类型
     * @param bussinessCode 业务编码
     * @param msg           需要发送的消息
     */
    public void send(String organization, String group, String businessType, String bussinessCode, String msg) {
        if (messageClientConfig.getChannelType() == MessageParameterStatic.CHANNELTYPE_MQ) {
            //新发送的消息，不需要返回
            producerSend(MessageParameterStatic.MESSAGETYPE_SEND, false, MessageParameterStatic.CHANNELTYPE_MQ, System.currentTimeMillis(), organization, group, businessType, bussinessCode, msg);
        } else {
//            messageClient.send(organization, group, businessType, bussinessCode, msg);
        }
    }

    /**
     * 消息发送并且需要应答
     * 参数全为空时，采用默认队列“QUEUE_DEFAULT”
     *
     * @param organization 组织编码
     * @param group        小组编码
     * @param businessType 业务类型
     * @param businessCode 业务编码
     * @param msg          需要发送的消息
     * @return String json
     */
    public String sendAndReceive(String organization, String group, String businessType, String businessCode, String msg) {
        // 如果用用MQ发送
        if (messageClientConfig.getChannelType() == MessageParameterStatic.CHANNELTYPE_MQ) {
            long createtime = System.currentTimeMillis();
            // 新发送的消息，但需要返回
            producerSend(MessageParameterStatic.MESSAGETYPE_SEND, true, MessageParameterStatic.CHANNELTYPE_MQ, createtime, organization, group, businessType, businessCode, msg);
            // key
            String strKey = MessageParameterStatic.CHANNELTYPE_MQ + "." + organization + "." + group + "." + businessType + "." + businessCode + "." + createtime;
            int tempTime = 0;
            while (tempTime < messageClientConfig.getTimeout()) {
                if (ConsumerHandlerImpl.needBackMessageMap.containsKey(strKey)) {
                    String returnMsg = ConsumerHandlerImpl.needBackMessageMap.get(strKey);
                    ConsumerHandlerImpl.needBackMessageMap.remove(strKey);
                    return returnMsg;
                }
                try {
                    Thread.sleep(messageClientConfig.getOnesleeptime());
                    tempTime += messageClientConfig.getOnesleeptime();
                } catch (Exception e) {
                }
            }
            return ReturnMessageUtils.returnMsgObject(ReturnMessageUtils.ERROR_1104,ReturnMessageUtils.ERROR_1104_NAME,"data service is timeout").toJSONString();
        } else {
//            return messageClient.sendAndReceive(organization, group, businessType, businessCode, msg);
            return null;
        }
    }

    public static ProducerConfiguration getProducerConfiguration() {
        return producerConfiguration;
    }

    public static void setProducerConfiguration(ProducerConfiguration producerConfiguration) {
        MQServer.producerConfiguration = producerConfiguration;
    }

    public static ConsumerConfig getConsumer() {
        return consumer;
    }

    public static void setConsumer(ConsumerConfig consumer) {
        MQServer.consumer = consumer;
    }
}
