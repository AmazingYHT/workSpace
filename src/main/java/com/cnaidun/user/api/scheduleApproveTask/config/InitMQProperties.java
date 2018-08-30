package com.cnaidun.user.api.scheduleApproveTask.config;

import com.cnaidun.messageclient.MQServer;
import com.cnaidun.user.api.scheduleApproveTask.service.MessageClientListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 10000)
public class InitMQProperties implements ApplicationRunner {

    @Autowired
    private MQServer mqServer ;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        mqServer.registerListener(new MessageClientListener());
    }
}
