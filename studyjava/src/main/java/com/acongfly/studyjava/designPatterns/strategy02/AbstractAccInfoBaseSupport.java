package com.acongfly.studyjava.designPatterns.strategy02;

/**
 * @program: fintech-payment
 * @description: 抽象方法渠道账户相关信息基础支持类
 * @author: shicong yang
 * @create: 2019-06-01 15:01
 **/
public interface AbstractAccInfoBaseSupport<T extends BaseAccInfoJson> {

    String buildAccInfo(T t);

    T readAccInfo(String json);

}
