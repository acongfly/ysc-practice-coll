package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shicongyang
 * @ClassName: ConditionCommuncation
 * @Description: condition通信(可以与wait 和 notify进行比较) 这个是对TraditionalThreadCommunication的修改
 * @date 2018年4月29日 下午11:49:26
 * @see com/acongfly/studyjava/thread/thread1/TraditionalThreadCommunication.java
 */
public class ConditionCommuncation {

    public static void main(String[] args) {
        Busi busi = new Busi();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    busi.sub(i);
                }
            }
        }).start();
        /*
         * busi.sub(i);
        	busi.main(i);如果采用这个写法的时候，需要注意的是两者的顺序，如果写反了，就导致死锁，主线程一直等待。需要注意
        	所以建议是对子线程创建一个新的线程，避免死锁
        	
         */
        for (int i = 0; i < 50; i++) {
            // busi.sub(i);
            busi.main(i);
        }
    }

}

class Busi {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private boolean shouldSub = true;

    public void sub(int i) {
        lock.lock();
        try {
            while (!shouldSub) {
                condition.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("子线程j=" + j + ",i=" + i);
            }
            // 设为false
            shouldSub = false;
            // 通知
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 最终都要执行一次解锁
        }
    }

    public void main(int i) {
        lock.lock();
        try {
            while (shouldSub) {
                condition.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("主线程j=" + j + ",i=" + i);
            }
            shouldSub = true;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}