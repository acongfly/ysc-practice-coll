package com.acongfly.studyjava.disruptorDemo;

import com.acongfly.studyjava.javaStudy.beancopy.BeanCopyUtils;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: study
 * @description: 订单生产者
 * @author: shicong yang
 * @create: 2019-04-26 18:12
 **/
public class OrderProduct {

    private RingBuffer<OrderInfo> ringBuffer;

    public OrderProduct(RingBuffer<OrderInfo> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(OrderInfo orderInfo) {
        //1.可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long next = ringBuffer.next();
        try {
            //2.用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
            OrderInfo order = ringBuffer.get(next);
            //3.获取要通过事件传递的业务数据
            BeanCopyUtils.map(orderInfo, order);
        } finally {
            //4.发布事件
            //注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
            // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(next);
        }

    }
}
