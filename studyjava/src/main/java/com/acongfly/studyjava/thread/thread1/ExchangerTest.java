package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shicongyang
 * @ClassName: ExchangerTest
 * @Description: exchange这个是用于两个人之间的数据交换所使用到的, 每个人在完成一定的事务后想和对方贾环数据
 * 那么第一个先拿出数据，将一直等待第二个人到来的时候才能彼此交换数据
 * @date 2018年4月30日 下午11:41:38
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Exchanger<String> exchanger = new Exchanger<String>();
        newCachedThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String data1 = "zhangsan";
                    System.out.println("线程：" + Thread.currentThread().getName() + "正在把数据" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 1000));
                    String data2 = exchanger.exchange(data1);
                    System.out.println("线程：" + Thread.currentThread().getName() + "换回来的数据是：" + data2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        newCachedThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String data2 = "lisi";
                    System.out.println("线程：" + Thread.currentThread().getName() + "正在把数据" + data2 + "换出去");
                    Thread.sleep((long) (Math.random() * 1000));
                    String data1 = exchanger.exchange(data2);
                    System.out.println("线程：" + Thread.currentThread().getName() + "换回来的数据是：" + data1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
