package com.acongfly.studyjava.designPatterns.observed01;

/**
 * program: study
 * <p>
 * description: 观察者二
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:27
 * <p>
 **/

public class ObserverTwo extends Observer {
    @Override
    public void update(String msg) {
        System.out.println(ObserverTwo.class.getName() + ":" + msg);
    }
}
