package com.acongfly.studyjava.thread.thread1;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author shicongyang
 * @ClassName: CallableAndFuture
 * @Description: callable创建的线程后可以返回结果，然后future去拿到结果
 * @date 2018年4月25日 下午11:30:37
 */
public class CallableAndFuture {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> future = threadPool.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(2000);
                        return "shicongyang";
                    }
                });
        try {
            System.out.println("等待结果：");
            System.out.println("拿到的结果是：" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //CompletionService是批量处理一部分线程，拿到结果，哪个先来就先取得哪个
        //打个比方就是：种了四块麦子，到了秋收的时候，哪块麦子好了就先收哪一块
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(newFixedThreadPool);        //实例化实现类
        //提交十个任务
        for (int i = 0; i < 10; i++) {
            final int task = i;
            completionService.submit(new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(6000));
                    return task;
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            try {
                Integer integer = completionService.take().get();            //获取到返回值
                System.out.println(integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

}
