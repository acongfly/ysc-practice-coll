package com.acongfly.kafkademo.consumer;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ysc-practice-coll
 * @description: 消费者拦截器
 * @author: shicong yang
 * @create: 2019-08-30 17:52
 **/
public class ConsumerinterceptorTTL implements ConsumerInterceptor<String, String> {

    //当前时间戳与请求的消息时间戳相差10秒即为过期，那么这条消息也就被过滤而不投递给具体的消费者
    private static final long EXPIRE_INTERVAL = 10 * 1000;

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        long now = System.currentTimeMillis();
        //过滤后新的
        Map<TopicPartition, List<ConsumerRecord<String, String>>> newRecords = new HashMap<>();
        for (TopicPartition tp : records.partitions()) {
            List<ConsumerRecord<String, String>> tpRecord = records.records(tp);
            List<ConsumerRecord<String, String>> newRecord = new ArrayList<>();
            for (ConsumerRecord<String, String> record : tpRecord) {
                if (now - record.timestamp() < EXPIRE_INTERVAL) {
                    newRecord.add(record);
                }

            }
            if (!newRecord.isEmpty()) {
                newRecords.put(tp, newRecord);
            }
        }

        return new ConsumerRecords<>(newRecords);
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        offsets.forEach((tp, offset) -> System.out.println(tp + ":" + offset.offset()));

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
