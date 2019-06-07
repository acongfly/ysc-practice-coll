package com.acongfly.studyjava.thread.thread1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author shicongyang
 * @ClassName: ExceptionTest
 * @Description: 由于线程的本质，使得你不能捕获从线程中逃逸的异常，一旦异常逃出任务的run()方法
 * 它就会向外传播到控制台，除非采取特殊步骤捕获这种错误
 * 为解决这个问题，我们修改executor产生线程的方式：
 * Thread.UncaughExceptionHandler是java se5中的新接口，它允许你在每个thread对象上都附着一个异常处理器
 * Thread.UncaughtException.uncaughtException会在线程因未捕获的异常而临近死亡的时候被调用
 * 使用它我们可以创建一个新类型的ThreadFactory,它将在每个新创建的Thread对象上附着一个Thread.UncaughExceptionHandler
 * @date 2018年5月15日 上午12:36:48
 */
public class ExceptionTest {

    public static void main(String[] args) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool(new HandlerThreadFactory());
        newCachedThreadPool.execute(new ExceptionThread2());
    }


}

class ExceptionThread2 implements Runnable {

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println("run()  by  " + currentThread);
        System.out.println("exception = " + currentThread.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught  = " + e);
    }

}

class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + "creating new Thread");
        Thread thread = new Thread(r);
        System.out.println("created " + thread);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("exception = " + thread.getUncaughtExceptionHandler());
        return thread;
    }

}
