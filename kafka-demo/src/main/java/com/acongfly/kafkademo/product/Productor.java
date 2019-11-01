package com.acongfly.kafkademo.product;

import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * kafka 生产者
 */

@Service
public class Productor {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaSendResultHandler producerListener;

    private static final String TOPIC = "topic-mq-test1";

    @Scheduled(fixedDelay = 10000)
    public String product() {
        ProductorEntity productorEntity = new ProductorEntity();
        for (int i = 0; i < 100; i++) {

            productorEntity.setAge(i);
            productorEntity.setMessage(System.currentTimeMillis() + "");
            productorEntity.setName("kafka" + i);
            try {
                /** 配置回调 */
                kafkaTemplate.setProducerListener(producerListener);
                /** 同步发送 */
                SendResult<String, Object> stringObjectSendResult =
                    kafkaTemplate.send(TOPIC, i % 5, "", productorEntity).get();
                System.out.println("stringObjectSendResult=" + stringObjectSendResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "Published successfully";
    }

}