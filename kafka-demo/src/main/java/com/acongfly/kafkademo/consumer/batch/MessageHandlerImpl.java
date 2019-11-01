package com.acongfly.kafkademo.consumer.batch;

import org.springframework.stereotype.Component;

@Component
public class MessageHandlerImpl implements IMessageHandler {
    @Override
    public void doHandler(MessageKafka messageKafka) {
        System.out.println("consume---->" + messageKafka);
    }
}