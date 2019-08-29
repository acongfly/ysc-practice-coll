package com.acongfly.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: ysc-practice-coll
 * @description: kafka consumer
 * @author: shicong yang
 * @create: 2019-08-28 14:47
 **/
@Slf4j
public class ConsumerMain3 {

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
        return props;
    }

    public static void main(String[] args) {
        Properties properties = initProperties();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> record = records.records(partition);
                    for (ConsumerRecord<String, String> crecord : record) {
                        System.out.println("topic=" + crecord.topic()
                                + ",partion = " + crecord.partition()
                                + ", offset = " + crecord.offset());
                        System.out.println("key = " + crecord.key()
                                + ", value = " + crecord.value());
                        //do some logical processing .
                    }

                    //提交方式3 按照分区粒度同步提交
                    long lastConsumedOffset = record.get(record.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastConsumedOffset + 1)));
                }

            }
        } catch (Exception e) {
            log.error("kafka consumer exception", e);
        } finally {
            consumer.close();
        }

    }


}
