// package com.acongfly.kafkademo.config;
//
// import org.springframework.context.annotation.Configuration;
//
/// ***
// *
// * @program: ysc-practice-coll
// * @description:
// * @author: shicong yang
// * @create: 2019-07-04 20:05
// **/
// @Configuration
// public class KafkaProducterConfigure {
//
// // @Bean
// // public ProducerFactory<String, Object> producerFactory() {
// // Map<String, Object> config = new HashMap<>();
// //
// // config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
// // config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
// // config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
// //
// // return new DefaultKafkaProducerFactory<>(config);
// // }
//
// // @Bean
// // public KafkaTemplate<String, Object> kafkaTemplate() {
// // return new KafkaTemplate<>(producerFactory());
// // }
//
// // 创建topic
// // @Bean
// // public NewTopic newTopic(){
// // return new NewTopic("topic-mq-test1",5, (short) 1);
// // }
//
// }
