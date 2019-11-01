package com.acongfly.studyjava.thread.threadStudy;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 线程工厂命名
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-04-18
 * <p>
 */

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_NUM = new AtomicInteger(0);
    private final AtomicInteger THREAD_NUM = new AtomicInteger(0);

    private final ThreadGroup group;
    private final boolean deamon;
    private final String prefixPattern;

    public NamedThreadFactory(String name, boolean deamon) {
        SecurityManager manager = System.getSecurityManager();
        if (manager != null) {
            this.group = manager.getThreadGroup();
        } else {
            this.group = Thread.currentThread().getThreadGroup();
        }
        this.deamon = deamon;
        this.prefixPattern = String.format("%s-%d-thread-%%s", name, POOL_NUM.getAndIncrement());
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r, String.format(this.prefixPattern, THREAD_NUM.getAndIncrement()), 0);
        t.setDaemon(this.deamon);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
