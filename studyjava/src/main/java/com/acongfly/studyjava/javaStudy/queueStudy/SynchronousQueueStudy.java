package com.acongfly.studyjava.javaStudy.queueStudy;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author shicongyang
 * @Description: SynchronousQueue 一种没有缓冲的队列，生产者产生的数据直接被消费者获取并消费
 * @date 2018/6/6 10:18
 */
public class SynchronousQueueStudy {
    public static void main(String[] args) {
        // 如果为 true，则等待线程以 FIFO 的顺序竞争访问；否则顺序是未指定的。
        // SynchronousQueue<Integer> sc =new SynchronousQueue<>(true);//fair
        SynchronousQueue<Integer> sc = new SynchronousQueue<>(); // 默认不指定的话是false，不公平的
        new Thread(() -> { // 生产者线程，使用的是lambda写法，需要使用JDK1.8
            while (true) {
                try {
                    sc.put(new Random().nextInt(50));
                    // 将指定元素添加到此队列，如有必要则等待另一个线程接收它。
                    System.out.println("sc.offer(new Random().nextInt(50)): " + sc.offer(new Random().nextInt(50)));
                    // 如果另一个线程正在等待以便接收指定元素，则将指定元素插入到此队列。如果没有等待接受数据的线程则直接返回false
                    System.out.println("sc.offer(2,5,TimeUnit.SECONDS):" + sc.offer(2, 5, TimeUnit.SECONDS));// 如果没有等待的线程，则等待指定的时间。在等待时间还没有接受数据的线程的话，直接返回false
                    System.out.println("添加操作运行完毕...");// 是操作完毕，并不是添加或获取元素成功!
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {// 消费者线程。使用的是lambda创建的线程写法需要使用jdk1.8
            while (true) {
                try {
                    System.out.println("-----------------> sc.take: " + sc.take());
                    System.out.println("-----------------> 获取操作运行完毕...");// 是操作完毕，并不是添加或获取元素成功!
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
