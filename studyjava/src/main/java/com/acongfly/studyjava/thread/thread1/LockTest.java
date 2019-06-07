package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shicongyang
 * @ClassName: LockTest
 * @Description: Lock学习（就像我们做高铁的时候使用的厕所，进一个人就上锁，用完厕所就开锁出去）
 * @date 2018年4月21日 下午7:32:33
 */
public class LockTest {

    public static void main(String[] args) {
        new LockTest().init();
    }

    private void init() {
        Output output = new Output();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.outputer("zhangsan");
                }
            }
        }).start();


        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.outputer("lisi");
                }
            }
        }).start();
    }


    class Output {
        Lock lock = new ReentrantLock();        //创建一把锁（全局）

        public void outputer(String name) {
            int len = name.length();
            lock.lock();                //加锁
            try {                        //使用try catch finally 是为了防止出现异常的时候这个lock也要释放开锁
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            } finally {
                lock.unlock();            //释放锁
            }
        }
    }
}
