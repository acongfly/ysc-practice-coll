package com.acongfly.kafkademo.consumer.batch;

import lombok.Data;

@Data
public class MessageKafka {

    private String topic;
    private Object content;
    private Object extendInfo;
    private boolean commitFlag;
}