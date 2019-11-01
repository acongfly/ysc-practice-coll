package com.acongfly.studyjava.javaStudy.atomicStudy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shicongyang
 * @Description: 原子类学习
 * @date 2018/6/5 10:29
 */
public class AtomicStudy {
    // 请求的客户端总数
    public static int clientTotal = 500;
    // 线程总数
    public static int threadTotal = 200;
    // 初始化原子类
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // Semaphore设定控制此此资源能同时可以被200个访问
        final Semaphore semaphore = new Semaphore(threadTotal);
        //
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("线程" + Thread.currentThread().getName() + "进入，当前有"
                            + (threadTotal - semaphore.availablePermits()) + "个并发");
                        add();
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将离开");
                        semaphore.release();
                        System.out.println("线程：" + Thread.currentThread().getName() + "已经离开，当前有"
                            + (threadTotal - semaphore.availablePermits()) + "个并发");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count=" + count.get());

    }

    private static void add() {
        count.incrementAndGet();
        System.out.println("原子类加一 操作，count=" + count.get());
    }

}
