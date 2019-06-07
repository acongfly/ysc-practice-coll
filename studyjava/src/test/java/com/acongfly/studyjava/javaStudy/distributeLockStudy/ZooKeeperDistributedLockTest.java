package com.acongfly.studyjava.javaStudy.distributeLockStudy;

import com.acongfly.studyjava.redis.distributedLock.DistributedLock;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZooKeeperDistributedLockTest {

    static int n = 500;

    public static void secskill() {
        System.out.println(--n);
    }

    @Test
    public void process() {

        ZooKeeperDistributedLock lock = new ZooKeeperDistributedLock("12345678");
        ///usr/local/var/run/zookeeper/data
//        lock.acquireDistributedLock();
        boolean b = lock.tryLock();
        if (b) {
        }
        System.out.println(b);
//        lock.unlock();

    }

    @Test
    public void acquireDistributedLock() {
    }

    @Test
    public void tryLock() {
    }

    @Test
    public void unlock() {
    }

    public static void main(String[] args) {


        Runnable runnable = new Runnable() {
            public void run() {
                ZooKeeperDistributedLock lock = null;
                try {
                    lock = new ZooKeeperDistributedLock("p1234567");
//                    lock.tryLock();
                    lock.acquireDistributedLock();
                    secskill();
//                    Thread.sleep(30000);
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}