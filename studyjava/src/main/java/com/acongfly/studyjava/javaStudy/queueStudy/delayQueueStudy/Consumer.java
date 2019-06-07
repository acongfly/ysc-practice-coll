package com.acongfly.studyjava.javaStudy.queueStudy.delayQueueStudy;

import java.util.concurrent.DelayQueue;

/**
 * @program: study
 * @description: 延迟队列消费者
 * @author: shicong yang
 * @create: 2019-04-30 15:10
 **/
public class Consumer implements Runnable {

    private DelayQueue<Message> delayQueue;

    public Consumer(DelayQueue<Message> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message take = delayQueue.take();
                System.out.println(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
