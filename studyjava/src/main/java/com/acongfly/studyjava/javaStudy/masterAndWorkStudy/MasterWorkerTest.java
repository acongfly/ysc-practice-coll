package com.acongfly.studyjava.javaStudy.masterAndWorkStudy;

import java.util.Random;

/**
 * @author shicongyang
 * @Description:
 * @date 2018/6/8 14:59
 */
public class MasterWorkerTest {

    public static void main(String[] args) {
        int processNum = Runtime.getRuntime().availableProcessors();
        System.out.println("可用的Processor数量：" + processNum);

        Master master = new Master(new MyWork(), processNum);
        Random random = new Random();

        for (int i = 0; i < processNum; i++) {
            Task task = new Task();
            task.setId(i);
            task.setName("任务" + i);
            task.setPrice(random.nextInt(10000));
            master.submit(task);
        }
        master.execute();

        long start = System.currentTimeMillis();
        while (true) {
            if (master.isComplete()) {
                long end = System.currentTimeMillis() - start;
                int result = master.getResult();
                System.out.println("最终结果：" + result + "耗时：" + end);
                if (processNum == result) {
                    System.out.println("处理结果成功");
                    break;
                }
            }
        }

    }
}
