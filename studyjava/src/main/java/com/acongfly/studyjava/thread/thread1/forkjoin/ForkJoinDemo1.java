package com.acongfly.studyjava.thread.thread1.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @program: ysc-practice-coll
 * @description: 算数（https://www.infoq.cn/article/fork-join-introduction/）
 * Fork/Join 框架提供了以下两个子类：
 * RecursiveAction：用于没有返回结果的任务。
 * RecursiveTask ：用于有返回结果的任务。
 * @author: shicong yang
 * @create: 2019-09-15 22:41
 **/
public class ForkJoinDemo1 extends RecursiveTask<Integer> {

    private int start;
    private int end;
    //阀值
    private int THRESHOLD = 2;

    public ForkJoinDemo1(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = end - start <= THRESHOLD;
        //任务小于阀值，就直接执行任务
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum = sum + i;

            }
        } else {
            //如果大于阀值，就进行任务拆分
            int middle = (start + end) / 2;
            ForkJoinDemo1 leftForkJoin = new ForkJoinDemo1(start, middle);
            ForkJoinDemo1 rightForkJoin = new ForkJoinDemo1(middle + 1, end);
            //执行子任务
            leftForkJoin.fork();
            rightForkJoin.fork();
            //等待子任务完成，并且获取到结果
            Integer left = leftForkJoin.join();
            Integer right = rightForkJoin.join();
            //合并sum

            sum = left + right;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成计算任务，计算1～1000
        ForkJoinDemo1 forkJoinDemo1 = new ForkJoinDemo1(1, 1000);
        //执行任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(forkJoinDemo1);

        try {
            Integer integer = result.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
