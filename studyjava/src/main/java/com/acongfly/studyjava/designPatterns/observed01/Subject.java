package com.acongfly.studyjava.designPatterns.observed01;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * program: study
 * <p>
 * description: 被观察者
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:30
 * <p>
 **/

public class Subject {
    private List<Observer> observers = Lists.newArrayList(); // 状态改变

    public void setMsg(String msg) {
        notifyAll(msg);
    }

    // 订阅
    public void addAttach(Observer observer) {
        observers.add(observer);
    }

    private void notifyAll(String msg) {
        for (Observer observer : observers) {
            observer.update(msg);
        }
    }

}
