package com.acongfly.kafkademo.product;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-08-15 16:51
 **/
public class ProductorMain1 {

    public static final String brokerList = "127.0.0.1:9092";

    public static final String topic = "blinklog";
    private static final long EXPIRE_INTERVAL = 10 * 1000;

    public static Properties initConfig() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static void main(String[] args) {

        Properties properties = initConfig();
        KafkaProducer<String, String> productor = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = null;

        // //模拟延时操作
        // ProducerRecord<String, String> record1 = new ProducerRecord<>(topic, 0, System.currentTimeMillis() -
        // EXPIRE_INTERVAL, null, "first-expire-data");
        // ProducerRecord<String, String> record2 = new ProducerRecord<>(topic, 0, System.currentTimeMillis(), null,
        // "normal-data");
        // ProducerRecord<String, String> record3 = new ProducerRecord<>(topic, 0, System.currentTimeMillis() -
        // EXPIRE_INTERVAL, null, "last-expire-data");
        try {
            // productor.send(record1).get();
            // productor.send(record2).get();
            // productor.send(record3).get();
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < 3; i++) {

                    LogInfo logInfo = new LogInfo();
                    logInfo.setBizMethod(i + "" + j);
                    logInfo.setReqMsg("test req");
                    logInfo.setRespMsg("test resp");
                    logInfo.setCreateTime(System.currentTimeMillis());
                    logInfo.setMethodStartTime(System.currentTimeMillis());
                    logInfo.setMethodEndTime(System.currentTimeMillis());
                    producerRecord = new ProducerRecord(topic, 0, "", JSON.toJSONString(logInfo));
                    productor.send(producerRecord).get();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
