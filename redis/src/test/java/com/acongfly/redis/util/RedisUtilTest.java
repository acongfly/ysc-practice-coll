package com.acongfly.redis.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Resource
    private RedisUtil RedisUtil;
    /**
     * KEY of payment submit channel id collection
     */
    String KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID = "pe:payment:submit:channel:id";

    @Test
    public void zAdd() {
        for (int i = 0; i < 10; i++) {
            Boolean aBoolean = RedisUtil.zAdd(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID, i + "", i);
            System.out.println(aBoolean);
        }
    }

    @Test
    public void zRange() {
        Set<String> strings = RedisUtil.zRange(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID, 0, -1);
        System.out.println(strings);
    }

    @Test
    public void zReverseRange() {
        Set<String> strings = RedisUtil.zReverseRange(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID, 0, 0);
        System.out.println(strings);
    }

    @Test
    public void zSize() {
        Long aLong = RedisUtil.zSize(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID);
        System.out.println(aLong);
    }

    @Test
    public void zRemoveRange() {
        Long removeRange = RedisUtil.zRemoveRange(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID, 0, -1);
        System.out.println(removeRange);
    }

    @Test
    public void lock() {
        boolean lock = RedisUtil.lock("testlock", "lock", 1);
        System.out.println(lock);
    }

    @Test
    public void unLock() {
        boolean b = RedisUtil.unLock("testlock", "lock");
        System.out.println(b);
    }

    @Test
    public void test() {
        String BUCKET_NAME_PREFIX = "ysc:DQ:bucket:";
        Set<ZSetOperations.TypedTuple<String>> typedTuples = RedisUtil.zRangeWithScores(KEY_PAYMENT_SUBMIT_CHANNEL_ZSET_ID, 0, 2);
        System.out.println(typedTuples);
    }

    public static void main(String[] args) {
        System.out.println(System.nanoTime() > 279165830154016L);
    }
}