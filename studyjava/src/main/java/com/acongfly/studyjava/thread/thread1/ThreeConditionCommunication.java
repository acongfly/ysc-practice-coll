package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shicongyang
 * @ClassName: ThreeConditionCommunication
 * @Description: 当多于两个condition的时候的例子，此处使用三次condition(要求，1完成2执行，2执行完3执行，3执行完成1执行)
 * @date 2018年4月30日 下午6:05:14
 */
public class ThreeConditionCommunication {
    public static void main(String[] args) {
        Busine busine = new Busine();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    busine.sub2(i);
                }
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    busine.sub3(i);
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            busine.sub1(i);
        }

    }

}

class Busine {
    Lock lock = new ReentrantLock();
    // 创建三个condition
    Condition condition = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    private int shouldSub = 1;

    public void sub2(int i) {
        lock.lock();
        try {
            while (shouldSub != 2) {
                condition2.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("sub2线程执行，j=" + j + ",i=" + i);
            }
            shouldSub = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sub3(int i) {
        lock.lock();
        try {
            while (shouldSub != 3) {
                condition3.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("sub3线程执行，j=" + j + ",i=" + i);
            }
            shouldSub = 1;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sub1(int i) {
        lock.lock();
        try {
            while (shouldSub != 1) {
                condition.await();
            }
            for (int j = 0; j < 10; j++) {
                System.out.println("sub1线程执行，j=" + j + ",i=" + i);
            }
            shouldSub = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
