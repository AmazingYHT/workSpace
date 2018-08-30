package com.cnaidun.messageclient.config;

import com.cnaidun.messageclient.config.RabbitMQConstants;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RabbitMQConnection {

    @Resource
    private RabbitMQConstants abbitMQConstants;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(abbitMQConstants.getHost());
        connectionFactory.setPort(abbitMQConstants.getPort());
        connectionFactory.setUsername(abbitMQConstants.getUsername());
        connectionFactory.setPassword(abbitMQConstants.getPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
