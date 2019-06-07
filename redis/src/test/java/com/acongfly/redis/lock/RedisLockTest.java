package com.acongfly.redis.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest {

    @Resource
    private RedisLock redisLock;

    @Test
    public void tryLock() {
        redisLock.tryLock("lock:orderId:12345678", "123456789", 60000);
    }

    @Test
    public void lock() {
        try {
            redisLock.lock("lock:orderId:12345678", "123456789");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock1() {
    }

    @Test
    public void tryLock1() {
    }

    @Test
    public void unlock() {
    }
}