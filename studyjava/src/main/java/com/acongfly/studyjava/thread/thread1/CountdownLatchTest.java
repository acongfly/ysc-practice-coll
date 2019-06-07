package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shicongyang
 * @ClassName: CountdownLatchTest
 * @Description: countdownLatch可以理解为一个倒数的计数器，调用这个对象的countDown方法就将计数器减一，当计数器
 * 减到0的时候，所有的等待者或者单个等待者就开始执行
 * 它可以实现一个人（也可以是多个人）等待其他所有人都来通知他，可以实现一个人通知多个人的效果，类似于裁判一声口令，
 * 运动员就开始奔跑，或者所有的运动员跑到终点裁判才公布结果
 * @date 2018年4月30日 下午11:08:47
 */
public class CountdownLatchTest {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch cdOrder = new CountDownLatch(1);        //这个是发号命令
        CountDownLatch cdAnswer = new CountDownLatch(3);    //这个是响应结果
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {

                    try {
                        System.out.println("线程：" + Thread.currentThread().getName() + "准备接受命令");
                        cdOrder.await();
                        System.out.println("线程：" + Thread.currentThread().getName() + "已经接受命令");
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "回应命令处理的结果");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程：" + Thread.currentThread().getName() + "即将发布命令");
            cdOrder.countDown();
            System.out.println("线程：" + Thread.currentThread().getName() + "已经发布命令，正在等待结果");
            cdAnswer.await();
            System.out.println("线程：" + Thread.currentThread().getName() + "已经接受到所有的响应结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
