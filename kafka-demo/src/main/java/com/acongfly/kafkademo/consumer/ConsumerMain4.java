package com.acongfly.kafkademo.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: ysc-practice-coll
 * @description: kafka consumer
 * @author: shicong yang
 * @create: 2019-08-28 14:47
 **/
@Slf4j
public class ConsumerMain4 {

    public static final String brokerList = "127.0.0.1:9092";
    public static final String topic = "topic-study-mq";
    public static final String groupid = "study-kafka-group";
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupid);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer.client.id.demo");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 此处配置消费端的拦截器
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerinterceptorTTL.class.getName());
        return props;
    }

    public static void main(String[] args) {
        Properties properties = initProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {

                    System.out.println("topic=" + record.topic() + ",partion = " + record.partition() + ", offset = "
                        + record.offset());
                    System.out.println("key = " + record.key() + ", value = " + record.value());
                    // do something to process record.
                }
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception == null) {
                            log.info("success offsets={}", offsets);
                        } else {
                            log.error("fail offset={}", offsets, exception);
                        }
                    }
                });

            }
        } catch (Exception e) {
            log.error("kafka consumer exception", e);
        } finally {
            consumer.close();
        }

    }

}
