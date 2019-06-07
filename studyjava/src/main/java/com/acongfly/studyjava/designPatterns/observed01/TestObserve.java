package com.acongfly.studyjava.designPatterns.observed01;

/**
 * program: study<p>
 * description: 测试观察者模式<p>
 * author: shicong yang<p>
 * createDate: 2019-01-09 16:33<p>
 **/

public class TestObserve {
    public static void main(String[] args) {
        ObserverOne observerOne = new ObserverOne();
        ObserverTwo observerTwo = new ObserverTwo();
        ObserverThree observerThree = new ObserverThree();
        Subject subject = new Subject();
        subject.addAttach(observerOne);
        subject.addAttach(observerTwo);
        subject.addAttach(observerThree);
        subject.setMsg("数据修改");
    }
}
