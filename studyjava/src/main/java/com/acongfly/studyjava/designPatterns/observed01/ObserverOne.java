package com.acongfly.studyjava.designPatterns.observed01;

/**
 * program: study
 * <p>
 * description: 观察者一
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:27
 * <p>
 **/

public class ObserverOne extends Observer {
    @Override
    public void update(String msg) {
        System.out.println(ObserverOne.class.getName() + ":" + msg);
    }
}
