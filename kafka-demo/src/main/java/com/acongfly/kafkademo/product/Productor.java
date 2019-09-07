package com.acongfly.kafkademo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * kafka 生产者
 */

@Service
public class Productor {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaSendResultHandler producerListener;


    private static final String TOPIC = "topic-study-mq";

    @Scheduled(fixedDelay = 10000)
    public String product() {
        ProductorEntity productorEntity = new ProductorEntity();
        for (int i = 0; i < 100; i++) {

            productorEntity.setAge(10);
            productorEntity.setMessage("test message");
            productorEntity.setName("kafka" + i);
            try {
                /**配置回调*/
                kafkaTemplate.setProducerListener(producerListener);
                /**同步发送*/
                SendResult<String, Object> stringObjectSendResult = kafkaTemplate.send(TOPIC, productorEntity).get();
//                System.out.println(stringObjectSendResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "Published successfully";
    }

}