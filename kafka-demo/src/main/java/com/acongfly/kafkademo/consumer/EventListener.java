package com.acongfly.kafkademo.consumer;

import cn.hutool.json.JSONUtil;
import com.acongfly.kafkademo.product.ProductorEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Slf4j
public class EventListener {

    private static final String TOPIC = "topic-study-mq";

    @KafkaListener(topics = TOPIC)
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            ProductorEntity productorEntity = JSONUtil.toBean(message, ProductorEntity.class);
            try {
                System.out.println(productorEntity);
//                System.out.println(message);
            } catch (Exception e) {
                log.error("event Exception", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

}
