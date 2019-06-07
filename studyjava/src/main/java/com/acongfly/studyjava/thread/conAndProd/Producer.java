package com.acongfly.studyjava.thread.conAndProd;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @Description:生产者 (通过阻塞队列实现)
 * @author: shicongyang
 * @date: 2018年3月22日 下午7:09:47
 */
public class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue q) {
        this.queue = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);  //模拟耗时
                queue.put(produce());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int produce() {
        int nextInt = new Random().nextInt();
        System.out.println("Thread:" + Thread.currentThread().getId() + "produce" + nextInt);
        return nextInt;
    }

}
