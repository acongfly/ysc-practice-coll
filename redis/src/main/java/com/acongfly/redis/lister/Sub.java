// package com.acongfly.redis.lister;
//
// import javax.annotation.Resource;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.connection.Message;
// import org.springframework.data.redis.connection.MessageListener;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
// import org.springframework.stereotype.Component;
//
// @Component
// public class Sub implements MessageListener {
//
// @Resource
// private StringRedisSerializer stringRedisSerializer;
//
// @Autowired
// private GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer;
//
// /* (非 Javadoc)
// * Description:
// * @see
// org.springframework.data.redis.connection.MessageListener#onMessage(org.springframework.data.redis.connection.Message,
// byte[])
// */
// @Override
// public void onMessage(Message message, byte[] pattern) {
// byte[] body = message.getBody();// 请使用valueSerializer
// byte[] channel = message.getChannel();
// String msg = (String)stringRedisSerializer.deserialize(body);
// String topic = (String)stringRedisSerializer.deserialize(channel);
// System.out.println("我是sub,监听" + topic + ",我收到消息：" + msg);
// }
//
// }