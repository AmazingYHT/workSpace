package com.cnaidun.messageclient.service.config;


import com.alibaba.fastjson.JSONObject;
import com.cnaidun.messageclient.utils.MessageContextUtils;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String exchange;
    private String routingKey;
    private String queueName;

    public ProducerConfiguration(){

    }

    public ProducerConfiguration(String exchange,String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
        this.exchange=exchange;
        if(null == this.rabbitTemplate)
            this.rabbitTemplate = MessageContextUtils.getBean(RabbitTemplate.class);
        this.rabbitTemplate.setRoutingKey(this.routingKey);
        this.rabbitTemplate.setQueue(this.queueName);

        RabbitAdmin admin = new RabbitAdmin(this.rabbitTemplate.getConnectionFactory());
        admin.declareQueue(new Queue(this.queueName));
        admin.declareExchange(new TopicExchange(this.exchange));
    }

    public void send(JSONObject msg) {
        this.rabbitTemplate.convertAndSend(msg);
    }

    public void send(String exchange,String routingKey,Object msg) {
        this.rabbitTemplate.convertAndSend(exchange,routingKey,msg);
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
