package com.acongfly.studyjava.designPatterns.observed01;

/**
 * program: study
 * <p>
 * description: 观察者三
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-09 16:27
 * <p>
 **/

public class ObserverThree extends Observer {
    @Override
    public void update(String msg) {
        System.out.println(ObserverThree.class.getName() + ":" + msg);
    }
}
