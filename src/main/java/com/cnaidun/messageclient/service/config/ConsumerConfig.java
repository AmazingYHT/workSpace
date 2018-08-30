package com.cnaidun.messageclient.service.config;


import com.cnaidun.messageclient.config.RabbitMQConnection;
import com.cnaidun.messageclient.service.ConsumerHandlerImpl;
import com.cnaidun.messageclient.service.listener.MessageReceiveListener;
import com.cnaidun.messageclient.utils.MessageContextUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsumerConfig {

    @Autowired
    private RabbitMQConnection rabbitMQConnection;

    private ConsumerHandlerImpl consumerHandlerImpl = new ConsumerHandlerImpl();

    public void registerListener(MessageReceiveListener messageReceiveListener){
        consumerHandlerImpl.registerListener(messageReceiveListener);
    }

    public ConsumerConfig(String queue,int numberOfConsumer) throws Exception {
        if(null == rabbitMQConnection)
            rabbitMQConnection = MessageContextUtils.getBean(RabbitMQConnection.class);

        ConsumerSimpleMessageListenerContainer container = new ConsumerSimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitMQConnection.connectionFactory());
        container.setQueueNames(queue);
        container.setConcurrentConsumers(numberOfConsumer);
        container.setMessageListener(new MessageListenerAdapter(consumerHandlerImpl));
        container.startConsumers();
    }

}
