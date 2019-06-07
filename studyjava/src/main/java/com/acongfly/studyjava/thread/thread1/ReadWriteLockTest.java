package com.acongfly.studyjava.thread.thread1;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shicongyang
 * @ClassName: ReadWriteLockTest
 * @Description: 读写锁练习
 * @date 2018年4月28日 下午8:30:04
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        Queue queue = new Queue();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        queue.get();
                    }
                }
            }).start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        queue.put(new Random().nextInt(1000));
                    }
                }
            }).start();
        }
    }
}

class Queue {
    //共享数据只能有一个线程能写数据，但是可以有多个线程同时读该数据
    private Object data = null;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void get() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备读取数据");
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + "读取到的数据：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();//释放锁
        }
    }

    public void put(Object data) {
        readWriteLock.writeLock().lock();       //写锁
        try {
            System.out.println(Thread.currentThread().getName() + "准备写数据");
            Thread.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "写的数据是：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();        //释放锁，针对无论是否出现异常
        }

    }

}