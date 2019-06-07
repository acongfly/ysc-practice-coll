package com.acongfly.studyjava.hystrix;

import com.netflix.hystrix.*;

/**
 * @program: study
 * @description: 获取消息线程隔离类-使用hystrix
 * @author: shicong yang
 * @create: 2019-04-12 20:09
 **/
public class GetInfoProcessCommand extends HystrixCommand<String> {


    private String orderId;

//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }

    //    programotected GetInfoCommand(HystrixCommandGroupKey group) {
//        super(group);
//    }
//
//    protected GetInfoCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool) {
//        super(group, threadPool);
//    }
//
//    protected GetInfoCommand(HystrixCommandGroupKey group, int executionIsolationThreadTimeoutInMilliseconds) {
//        super(group, executionIsolationThreadTimeoutInMilliseconds);
//    }
//
//    protected GetInfoCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds) {
//        super(group, threadPool, executionIsolationThreadTimeoutInMilliseconds);
//    }

    protected GetInfoProcessCommand(String orderId) {
        super(setter());
        this.orderId = orderId;
    }


    private static Setter setter() {

        //服务分组
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("info");
        //服务标识
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("getInfo");
        //线程池名称
        HystrixThreadPoolKey hystrixThreadPoolKey = HystrixThreadPoolKey.Factory.asKey("info-pool");
        //线程池配置
        HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter().withCoreSize(10)
                .withMaxQueueSize(Integer.MAX_VALUE)
                .withKeepAliveTimeMinutes(5)
                .withQueueSizeRejectionThreshold(1000);

        //命令属性配置
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withExecutionTimeoutInMilliseconds(60000);

        return Setter
                .withGroupKey(groupKey)
                .andCommandKey(commandKey)
                .andCommandPropertiesDefaults(commandProperties)
                .andThreadPoolKey(hystrixThreadPoolKey)
                .andThreadPoolPropertiesDefaults(threadPoolProperties);

    }

    @Override
    protected String run() throws Exception {
        System.out.println("==========orderId" + orderId);
        Thread.sleep(5000);
        return orderId;
    }

    /**
     * 异常回滚默认值
     *
     * @return
     */
    @Override
    protected String getFallback() {
        return super.getFallback();
    }
}
