package com.acongfly.kafkademo.consumer;

import cn.hutool.json.JSONUtil;
import com.acongfly.kafkademo.product.ProductorEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;


@Component
@Slf4j
public class EventListener {
    //https://www.jianshu.com/p/6a44da908e48

    private static final String TOPIC = "topic-study-mq";


    @KafkaListener(topics = TOPIC, groupId = "groupId1")
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            try {
                System.out.println("grout1============" + message);
            } catch (Exception e) {
                log.error("event Exception", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

    @KafkaListener(topics = TOPIC, groupId = "groupId2")
    public void listen2(ConsumerRecord<?, ?> record, Acknowledgment ack) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            try {
                System.out.println("grout2============" + message);
            } catch (Exception e) {
                log.error("event Exception", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

}
