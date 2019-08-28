package com.acongfly.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: ysc-practice-coll
 * @description: kafka consumer
 * @author: shicong yang
 * @create: 2019-08-28 14:47
 **/
@Slf4j
public class ConsumerMain2 {

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

                    System.out.println("topic=" + record.topic()
                            + ",partion = " + record.partition()
                            + ", offset = " + record.offset());
                    System.out.println("key = " + record.key()
                            + ", value = " + record.value());
                    //do something to process record.
                }
            }
        } catch (Exception e) {
            log.error("kafka consumer exception", e);
        } finally {
            consumer.close();
        }

    }


}
