package com.acongfly.studyjava.disruptorDemo;

import com.lmax.disruptor.EventFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: study
 * @description: 订单工厂
 * @author: shicong yang
 * @create: 2019-04-26 18:10
 **/
@Slf4j
public class OrderInfoFactory implements EventFactory<OrderInfo> {
    @Override
    public OrderInfo newInstance() {
        // log.info("订单工厂初始化数据......");
        return new OrderInfo();
    }
}
