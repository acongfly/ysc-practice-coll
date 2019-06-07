package com.acongfly.studyjava.disruptorDemo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ThreadFactory;

/**
 * @program: study
 * @description: 创建订单数据
 * @author: shicong yang
 * @create: 2019-04-26 19:42
 **/
@Service
public class CreateOrderInfo {

    private OrderInfoFactory orderInfoFactory;

    //创建bufferSize ,也就是RingBuffer大小，必须是2的N次方
    private static final int ringBufferSize = 1024 * 1024;

    private Disruptor<OrderInfo> disruptor;

    @PostConstruct
    public void init() {
        //创建工厂
        orderInfoFactory = new OrderInfoFactory();
        ThreadFactory orderInfoTreadFactory = new ThreadFactoryBuilder().setNameFormat("order-info-%d").build();
        //ProducerType.SINGLE 一对一 一个生产者对应一个消费者
        disruptor = new Disruptor<OrderInfo>(orderInfoFactory, ringBufferSize, orderInfoTreadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());
        // 连接消费事件方法
        disruptor.handleEventsWith(new OrderConsumer());
        // 启动
        disruptor.start();
    }

    public void createOrder(OrderInfo orderInfo) {
        /**
         //BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
         //SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
         //YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
         */
        //Disruptor 的事件发布过程是一个两阶段提交的过程：
        //发布事件
        RingBuffer<OrderInfo> ringBuffer = disruptor.getRingBuffer();
        OrderProduct product = new OrderProduct(ringBuffer);

        product.onData(orderInfo);
//        disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
    }

    @PreDestroy
    public void destroy() {
        disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
    }

}
