package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shicongyang
 * @ClassName: CyclicBarrierTest
 * @Description: CyclicBarrier此工具类的使用，类似于 好几个人出去玩，约定一个地方集合，先来的人要等后面的人来齐了，一起出发。这个类就是这个集合的概念
 * @date 2018年4月30日 下午9:48:05
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {

                    try {
                        Thread.sleep((long)(Math.random() * 1000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合地点1，当前已有"
                            + (cyclicBarrier.getNumberWaiting() + 1) + "个已经到达，"
                            + (cyclicBarrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        cyclicBarrier.await();

                        Thread.sleep((long)(Math.random() * 1000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合地点2，当前已有"
                            + (cyclicBarrier.getNumberWaiting() + 1) + "个已经到达，"
                            + (cyclicBarrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        cyclicBarrier.await();

                        Thread.sleep((long)(Math.random() * 1000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合地点3，当前已有"
                            + (cyclicBarrier.getNumberWaiting() + 1) + "个已经到达，"
                            + (cyclicBarrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.execute(runnable);
        }

        threadPool.shutdown();
    }

}
