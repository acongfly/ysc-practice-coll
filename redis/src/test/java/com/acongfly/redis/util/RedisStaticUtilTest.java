package com.acongfly.redis.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisStaticUtilTest {

    @Test
    public void lock() {
        boolean lock = RedisStaticUtil.lock("testlock", "lock", 1);
        System.out.println(lock);
    }

    @Test
    public void unLock() {
        boolean b = RedisStaticUtil.unLock("testlock", "lock");
        System.out.println(b);
    }

    @Test
    public void testKeys() {
        // Set<String> keys = RedisStaticUtil.keys("ysc:DQ:jp:*");
        // System.out.println(keys.size());
        Long aLong = RedisStaticUtil.zSize("ysc:DQ:bucket:testQueue0");
        System.out.println(aLong);
        RedisStaticUtil.delete("ysc:DQ:bucket:testQueue0");
    }

}