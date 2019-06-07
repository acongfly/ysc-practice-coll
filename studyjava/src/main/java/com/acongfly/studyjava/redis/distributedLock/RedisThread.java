package com.acongfly.studyjava.redis.distributedLock;

/**
 * program: study<p>
 * description: <p>
 * author: shicong yang<p>
 * createDate: 2018-11-09 16:32<p>
 **/

public class RedisThread extends Thread {

    private RedisServiceTest redisServiceTest;

    public RedisThread(RedisServiceTest redisServiceTest) {
        this.redisServiceTest = redisServiceTest;
    }

    @Override
    public void run() {
        redisServiceTest.seckill();
    }
}
