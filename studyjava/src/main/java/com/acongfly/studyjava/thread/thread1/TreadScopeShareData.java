package com.acongfly.studyjava.thread.thread1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author shicongyang
 * @ClassName: TreadScopeShareData
 * @Description: 线程范围内共享变量
 * @date 2018年4月22日 上午11:14:13
 */
public class TreadScopeShareData {

    //	private static int data = 0;
    private static Map<Thread, Integer> threadData = new HashMap<>();    //线程间数据隔离

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "data=" + data);
                    threadData.put(Thread.currentThread(), data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("A" + Thread.currentThread().getName() + "data=" + data);
        }
    }

    static class B {
        public void get() {
            int data = threadData.get(Thread.currentThread());
            System.out.println("B" + Thread.currentThread().getName() + "data=" + data);
        }
    }

}
