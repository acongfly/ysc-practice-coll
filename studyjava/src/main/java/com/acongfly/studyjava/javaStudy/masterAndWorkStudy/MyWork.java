package com.acongfly.studyjava.javaStudy.masterAndWorkStudy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shicongyang
 * @Description: 为了使程序更灵活，将具体的业务执行逻辑抽离，在具体的Worker对象去实现
 * @date 2018/6/8 14:30
 */
public class MyWork extends Worker {

    public static Object handle(Task task) {
        Object result = null;
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            //此处是进行业务逻辑处理，比如是进行数据库操作，加工数据等操作
            Thread.sleep(5000);
            System.out.println("传入的task值为：" + task.toString());
            //此处成功后进行加一操作，用以判断是否处理成功，正式项目中可以使用响应码和响应信息
            result = atomicInteger.incrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
