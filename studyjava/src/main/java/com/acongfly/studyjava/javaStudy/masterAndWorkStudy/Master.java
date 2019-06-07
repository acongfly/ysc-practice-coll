package com.acongfly.studyjava.javaStudy.masterAndWorkStudy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shicongyang
 * @Description: master
 * @date 2018/6/7 17:28
 */
public class Master {

    /**
     * 1.使用一个ConcurrentLinkedQueue集合封装所有需要执行的任务
     */
    private ConcurrentLinkedQueue<Task> workerQueue = new ConcurrentLinkedQueue<>();

    /**
     * 2.使用一个hashMap来接收所有的worker对象
     */
    private Map<String, Thread> workers = new HashMap<>();

    /**
     * 3.使用一个ConcurrentHashMap来接收所有的worker的执行结果集
     */
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

    /**
     * 4.构造方法
     */

    public Master(Worker worker, int workerCount) {
        // 每一个worker对象都需要有Master的引用，workQueue用于任务的领取，resultMap用于任务的提交
        worker.setWorkQueue(this.workerQueue);
        worker.setResultMap(this.resultMap);

        for (int i = 0; i < workerCount; i++) {
            workers.put("子节点" + Integer.toString(i), new Thread(worker));
        }
    }

    /**
     * 提交方法
     *
     * @param task
     */
    public void submit(Task task) {
        this.workerQueue.add(task);
    }

    /**
     * 需要有一个执行的方法（启动应用程序，让所有的worker工作）
     */
    public void execute() {
        for (Map.Entry<String, Thread> m : workers.entrySet()) {
            m.getValue().start();
        }
    }

    /**
     * 判断线程是否结束，结束返回true,否则返回false
     *
     * @return
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> m : workers.entrySet()) {
            // 判断所有的线程状态是否属于已停止状态
            if (m.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    public int getResult() {
        int resultNum = 0;
        for (Map.Entry<String, Object> m : resultMap.entrySet()) {
            //汇总处理逻辑
            resultNum += (Integer) m.getValue();
        }
        return resultNum;
    }


}
