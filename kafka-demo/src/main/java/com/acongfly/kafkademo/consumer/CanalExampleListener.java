package com.acongfly.kafkademo.consumer;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.acongfly.kafkademo.product.ProductorEntity;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CanalExampleListener {

    private static final String TOPIC = "topic-mq-test1";

    @KafkaListener(topics = TOPIC, errorHandler = "errorHandler")
    public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) throws RuntimeException {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = String.valueOf(kafkaMessage.get());
            ProductorEntity productorEntity = JSONUtil.toBean(message, ProductorEntity.class);
            try {
                System.out.println("message========" + message);
                System.out.println("product+++++========" + productorEntity);
                // if (productorEntity.getAge() == 20)
                // throw new RuntimeException("error test");
                // 此处如不进行ack，则项目重启后，还会从原始offset出进行处理
                ack.acknowledge();
            } catch (Exception e) {
                log.error("event Exception", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

    @Bean
    public ConsumerAwareListenerErrorHandler errorHandler() {
        return (m, e, c) -> {
            MessageHeaders headers = m.getHeaders();
            log.info("consumerAwareErrorHandler receive : " + m.getPayload().toString());
            c.seek(
                new TopicPartition(headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                    headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)),
                headers.get(KafkaHeaders.OFFSET, Long.class));
            return null;
        };
    }
}
