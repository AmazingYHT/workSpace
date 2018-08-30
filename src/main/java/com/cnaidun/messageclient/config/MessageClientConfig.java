package com.cnaidun.messageclient.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix="cnaidun.messageclient")
public class MessageClientConfig {

    private int channelType = 1;

    private String mqname = "CNAIDUN.DATABASE";

    private int consumerNumber = 2;

    private int cacheMessageSize = 10000;

    private int timeout = 50000;

    private int onesleeptime = 100;

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getMqname() {
        return mqname;
    }

    public void setMqname(String mqname) {
        this.mqname = mqname;
    }

    public int getConsumerNumber() {
        return consumerNumber;
    }

    public void setConsumerNumber(int consumerNumber) {
        this.consumerNumber = consumerNumber;
    }

    public int getCacheMessageSize() {
        return cacheMessageSize;
    }

    public void setCacheMessageSize(int cacheMessageSize) {
        this.cacheMessageSize = cacheMessageSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getOnesleeptime() {
        return onesleeptime;
    }

    public void setOnesleeptime(int onesleeptime) {
        this.onesleeptime = onesleeptime;
    }
}
