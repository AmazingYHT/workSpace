package com.cnaidun;

//import org.activiti.spring.boot.SecurityAutoConfiguration;
import com.cnaidun.user.api.scheduleApproveTask.config.WxReceiver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootApplication
		//(exclude = SecurityAutoConfiguration.class)
//@EnableEurekaClient

@WebAppConfiguration
@MapperScan({"com.cnaidun.user.api.scheduleApproveTask.mapper","com.cnaidun.messageclient.controller"})
public class ScheduleApproveTaskApplication {
//@EnableFeignClients
	public static void main(String[] args) {
				SpringApplication.run(ScheduleApproveTaskApplication.class, args);
	}
}
