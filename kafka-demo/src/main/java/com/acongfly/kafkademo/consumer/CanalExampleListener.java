package com.acongfly.kafkademo.consumer;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.acongfly.kafkademo.product.ProductorEntity;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CanalExampleListener {

    private static final String TOPIC = "example";

    @KafkaListener(topics = TOPIC)
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            ProductorEntity productorEntity = JSONUtil.toBean(message, ProductorEntity.class);
            try {
                System.out.println(message);
                System.out.println(productorEntity);
            } catch (Exception e) {
                log.error("event Exception", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

}
