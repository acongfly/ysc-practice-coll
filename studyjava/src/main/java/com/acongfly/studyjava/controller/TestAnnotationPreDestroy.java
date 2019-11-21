package com.acongfly.studyjava.controller;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * description:系统停止后执行的方法
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-11-05
 * <p>
 */
@Component
public class TestAnnotationPreDestroy {

    @PreDestroy
    public void destory() {
        System.out.println("我被销毁了、、、、、我是用的@PreDestory的方式、、、、、、");
        System.out.println("我被销毁了、、、、、我是用的@PreDestory的方式、、、、、、");
    }
}