package com.acongfly.studyjava.thread.conAndProd;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer2).start();
    }

}
