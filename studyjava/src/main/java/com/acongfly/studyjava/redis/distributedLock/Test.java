package com.acongfly.studyjava.redis.distributedLock;

/**
 * program: study<p>
 * description: <p>
 * author: shicong yang<p>
 * createDate: 2018-11-09 16:36<p>
 **/

public class Test {
    public static void main(String[] args) {
        RedisServiceTest redisServiceTest = new RedisServiceTest();
        for (int i = 0; i < 50; i++) {
            RedisThread redisThread = new RedisThread(redisServiceTest);
            redisThread.start();
        }
    }
}
