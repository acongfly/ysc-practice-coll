package com.acongfly.studyjava.designPatterns.observed01;

/**
 * program: study
 * <p>
 * description: 观察者接口 我理解的就是观察者订阅被观察者的状态，当被观察者状态改变的时候会通知所有订阅的观察者的过程
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:26
 * <p>
 **/

public abstract class Observer {

    public abstract void update(String msg);
}
