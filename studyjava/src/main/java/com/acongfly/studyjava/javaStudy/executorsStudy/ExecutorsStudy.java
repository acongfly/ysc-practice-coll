package com.acongfly.studyjava.javaStudy.executorsStudy;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @author shicongyang
 * @Description: 线程池学习
 * @date 2018/6/12 10:02
 */
public class ExecutorsStudy {

    public static void main(String[] args) {

        /**
         * 此处引用com.google.guava包，定义线程的名称
         */
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
            // 核心线程数--线程池初始化创建的线程数量
            1,
            // 最大线程数，线程池中能创建的最大线程数
            2,
            // 连接存活时间（没有线程时候的等待时间）
            0,
            // 时间单位
            TimeUnit.SECONDS
            // 使用有界队列
            /**
             * 使用有界队列的时候：1.当有新任务来的时候，如果线程池中的线程是小于corePoolSize的话，就去创建一个新线程 2.当大于corePoolSize的时候，就会将多余的线程存放在有界队列中
             * 3.如果有界队列满了，那么就将剩下的线程数与maximumPoolSize进行比较，如果小于则创建一个线程 4.如果是大于maximumPoolSize那么就执行拒绝策略
             */
            // , new ArrayBlockingQueue<>(3)
            , new LinkedBlockingQueue<>()
            // ThreadFactory，可以后面使用此方法设置名称或者是否是后台进程等等
            , threadFactory
            // 拒绝策略
            , new ThreadPoolExecutor.CallerRunsPolicy());

        Task task1 = new Task(1, "task1");
        Task task2 = new Task(2, "task2");
        Task task3 = new Task(3, "task3");
        Task task4 = new Task(4, "task4");
        Task task5 = new Task(5, "task5");
        Task task6 = new Task(6, "task6");
        Task task7 = new Task(7, "task7");
        Task task8 = new Task(8, "task8");

        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);
        executorService.execute(task4);
        executorService.execute(task5);
        executorService.execute(task6);
        executorService.execute(task7);
        executorService.execute(task8);

        executorService.shutdown(); // 关闭

    }
}
