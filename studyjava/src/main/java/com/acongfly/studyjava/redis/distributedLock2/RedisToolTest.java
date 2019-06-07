package com.acongfly.studyjava.redis.distributedLock2;

import com.acongfly.studyjava.redis.distributedLock.DistributedLock;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

/**
 * program: study<p>
 * description: <p>
 * author: shicong yang<p>
 * createDate: 2018-11-13 15:56<p>
 **/

public class RedisToolTest {

    private static JedisPool pool = null;
    int n = 500;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(200);
        //设置最大空闲数
        config.setMaxIdle(8);
        //设置最大等待时间
        config.setMaxWaitMillis(1000 * 1000);
        //在borrow一个jedis实例时，是否需要验证，若为true,则所有的jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "10.2.9.9", 6379, 3000, "crs-fvh0000e:zhidao2018");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = UUID.randomUUID().toString();
                    boolean resources = RedisTool.tryGetDistributedLock(pool.getResource(), "resources", s, 1000);
                    if (resources) {
                        System.out.println(Thread.currentThread().getName() + "保存成功");
                    }
                    RedisTool.releaseDistributedLock(pool.getResource(), "resources", s);

                }
            }).start();

        }

    }
}
