// package com.acongfly.redis.lister;
//
// import javax.annotation.Resource;
//
// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
// import org.springframework.stereotype.Service;
//
// @Service
// public class Pub {
//
// @Resource
// private StringRedisTemplate stringRedisTemplate;
//
// @Resource
// private GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer;
//
// @Resource
// private JdkSerializationRedisSerializer jdkSerializationRedisSerializer;
//
// public void sendMessage(String channel, String message) {
//
// stringRedisTemplate.convertAndSend(channel, message);
// }
// }
//
//// public void sendMessage(String channel, User user) {
//// byte[] msg = jackson2JsonRedisSerializer.serialize(user);
//// rt.convertAndSend(channel, new String(msg));
////
////// rt.convertAndSend(channel, user);
//// }
