package com.acongfly.mqdemo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
// @RabbitListener 监听 okong 队列
@RabbitListener(queues = "okong")
@Slf4j
public class Consumer {

    /**
     * @param message
     * @RabbitHandler 指定消息的处理方法
     */
    @RabbitHandler
    public void process(String message) {
        log.info("接收的消息为: {}", message);
    }
}