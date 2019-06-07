package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shicongyang
 * @ClassName: ThreadPoolTest
 * @Description: 线程池学习
 * @date 2018年4月25日 下午9:49:57
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        //创建线程池
//		ExecutorService threadPool = Executors.newFixedThreadPool(5);		//固定线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();        //可变的线程池
//		ExecutorService threadPool = Executors.newSingleThreadExecutor();		//这个是单线程的，其实也就不存在线程池的说法的，但是有一点是，当这个线程死亡后，会自动重启一个线程保证总有一个线程
        for (int i = 0; i < 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        //为防止看不清，睡一会
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + ",j=" + j + ",i=" + task);
                    }

                }
            });
        }
        System.out.println("十个任务完成");
//		threadPool.shutdown();             //任务完成关闭线程池

        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(            //固定的计划执行线程
                new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("炸弹炸了");
                    }
                },
                8,        //等待8秒
                2,        //后面每隔2秒炸一次
                TimeUnit.SECONDS);            //秒
    }


}
