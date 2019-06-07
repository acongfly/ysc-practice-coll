package com.acongfly.studyjava.javaStudy.masterAndWorkStudy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shicongyang
 * @Description:
 * @date 2018/6/8 14:18
 */
public class Worker implements Runnable {

    private ConcurrentLinkedQueue<Task> workQueue;
    private ConcurrentHashMap<String, Object> resultMap;

    public ConcurrentLinkedQueue<Task> getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public ConcurrentHashMap<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }


    @Override
    public void run() {
        while (true) {
            Task task = this.workQueue.poll();
            if (task == null) {
                break;
            }
            //此处进行业务逻辑的处理,此处mywork继承worker
            Object result = MyWork.handle(task);
            this.resultMap.put(Integer.toString(task.getId()), result);
        }
    }

}
