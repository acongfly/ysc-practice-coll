package com.acongfly.studyjava.redis.distributedLock;

import org.apache.commons.collections.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * program: study<p>
 * description: 分布式锁的简单实现代码<p>
 * author: shicong yang<p>
 * createDate: 2018-11-09 14:13<p>
 **/

public class DistributedLock {

    private final JedisPool jedisPool;

    public DistributedLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * description: 加锁 <p>
     * param: [lockName 锁的key, acquireTimeout 获取超时时间, timeout 锁的超时时间] <p>
     * return: java.lang.String <p>
     * author: shicong yang<p>
     * date: 2018/11/9 <p>
     */
    public String lockWithTimeout(String lockName, long acquireTimeout, long timeout) {

        Jedis conn = null;
        String retIdentifier = null;

        try {
            //获取连接
            conn = jedisPool.getResource();
            //锁名，即key值
            String lockkey = "lock:" + lockName;
            //随机生成一个value
            String identifier = UUID.randomUUID().toString();

            //超时时间，上锁后超过此时间则自动释放
            int lockExpire = (int) (timeout / 10);

            //获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                //SETNX：SETNX key val：当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。
                if (conn.setnx(lockkey, identifier) == 1) {
                    //expire：expire key timeout：为key设置一个超时时间，单位为second，超过这个时间锁会自动释放，避免死锁。
                    conn.expire(lockkey, lockExpire);
//                    System.out.println("---------------设置值："+identifier);
                    //返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                //返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockkey) == -1) {
                    conn.expire(lockkey, lockExpire);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * description: 释放锁 <p>
     * param: [lockName 锁的key, identifier 释放锁的标识] <p>
     * return: boolean <p>
     * author: shicong yang<p>
     * date: 2018/11/9 <p>
     */
    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockkey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            String value = conn.get(lockkey);
            if (value == null) {
                return false;
            }
            if (identifier == null) {
                return false;
            }
            while (true) {
                //监视lock,准备开始事务
                conn.watch(lockkey);
                //通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(value)) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockkey);
                    List<Object> results = transaction.exec();
                    if (CollectionUtils.isEmpty(results)) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }


}
