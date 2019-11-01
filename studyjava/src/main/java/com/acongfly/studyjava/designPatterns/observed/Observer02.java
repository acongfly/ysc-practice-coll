package com.acongfly.studyjava.designPatterns.observed;

/***
 *
 * @Description: 观察者2号
 * @author shicongyang
 * @date 2018年4月16日 下午5:46:25
 *
 */
public class Observer02 implements Person {

    private String name = "李四";

    public Observer02() {}

    @Override
    public void getMessage(String s) {
        System.out.println(name + "收到王五的信息是:" + s);
    }

}
