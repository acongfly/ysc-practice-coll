package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author shicongyang
 * @ClassName: SemaphoreTest
 * @Description: Semaphore信号灯同步工具
 * 在什么地方用呢？举个例子:当要有十个人去厕所的时候，但是只有五个坑，只能运行五个人用，然后谁先
 * 用完谁走，然后谁在接着用。(比较俗)另外等待的五个人可以是随机获取优先的机会，也可以是按照先来后到的顺序获得机会
 * 这取决于构造semaphore对象时传入的参数选项
 * 单个信号量的semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得锁，再有另外的一个线程释放锁，这可应用于死锁恢复的一些场合
 * @date 2018年4月30日 下午7:46:42
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("线程：" + Thread.currentThread().getName() + "进入，当前已有：" + (3 - semaphore.availablePermits()) + "个并发");
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将离开");
                        semaphore.release();           //释放
                        //下面的代码有时候执行不是准确的，因为并没有和上面的代码合成原子单元
                        System.out.println("线程：" + Thread.currentThread().getName() + "已经离开，当前已有" + (3 - semaphore.availablePermits()) + "个并发");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            threadPool.execute(runnable);
        }
    }
}
