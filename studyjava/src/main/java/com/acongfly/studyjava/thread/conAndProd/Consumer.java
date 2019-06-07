package com.acongfly.studyjava.thread.conAndProd;

import java.util.concurrent.BlockingQueue;

/**
 * @Description:消费者（通过阻塞队列实现）
 * @author: shicongyang
 * @date: 2018年3月23日 下午5:26:07
 */
public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue q) {
        this.queue = q;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Thread.sleep(2000);            //模拟耗时
                consume(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void consume(Integer n) {
        System.out.println("thread:" + Thread.currentThread().getId() + "Consume" + n);

    }
}
