package com.acongfly.kafkademo.consumer.batch;

public interface IMessageHandler {
    void doHandler(MessageKafka messageKafka);
}