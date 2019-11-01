package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author shicongyang
 * @ClassName: BlockingQueueTest
 * @Description: 阻塞队列（BlockingQueue）
 * @date 2018年5月1日 下午6:14:57
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((long)(Math.random() * 1000));
                            System.out.println("线程" + Thread.currentThread().getName() + "准备放数据");
                            queue.put(1);
                            System.out.println(
                                "线程" + Thread.currentThread().getName() + "已经放数据，" + "队列目前有：" + queue.size() + "个数据");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("线程" + Thread.currentThread().getName() + "准备取出数据");
                        queue.take();
                        System.out.println(
                            "线程" + Thread.currentThread().getName() + "已经取走数据，" + "队列目前有：" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
