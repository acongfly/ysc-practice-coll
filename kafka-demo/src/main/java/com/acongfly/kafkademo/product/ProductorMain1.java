package com.acongfly.kafkademo.product;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-08-15 16:51
 **/
public class ProductorMain1 {

    public static final String brokerList = "127.0.0.1:9092";

    public static final String topic = "topic-study-mq";

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
        ProducerRecord<String, String> producerRecord = new ProducerRecord(topic, "hello kafka!!!!");
        try {
            productor.send(producerRecord).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
