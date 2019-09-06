package com.acongfly.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: ysc-practice-coll
 * @description: kafka consumer
 * 如果消费组 内 的消费者在启动的时候 能够找到消费位移 ，
 * 除非发生位移越界 ， 否 则 auto . offset. reset 参数并不会奏效，
 * 此时如 果想指定从开头或末尾开始消费，就需要 seek() 方法的帮助了 ，
 * 下面代码 用来指定从分区末尾开始消费。
 * @author: shicong yang
 * @create: 2019-08-28 14:47
 **/
@Slf4j
public class ConsumerMain5 {

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
            Set<TopicPartition> assignment = new HashSet<>();

            while (assignment.size() == 0) {
                consumer.poll(Duration.ofMillis(100));
                assignment = consumer.assignment();
            }
//            endOffsets()方法用来获取指定分区的末尾的消息位置,如下:
//            注意这里获取 的不是 8，是将要写入最新消息 的位置。
            Map<TopicPartition, Long> offsets = consumer.endOffsets(assignment);
            for (TopicPartition tp : assignment) {
                consumer.seek(tp, offsets.get(tp));
            }
        } catch (Exception e) {
            log.error("kafka consumer exception", e);
        } finally {
            consumer.close();
        }

    }


}
