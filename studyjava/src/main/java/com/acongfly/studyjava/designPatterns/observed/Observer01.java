package com.acongfly.studyjava.designPatterns.observed;

/***
 *
 * @Description: 观察者1号
 * @author shicongyang
 * @date 2018年4月16日 下午5:46:25
 *
 */
public class Observer01 implements Person {

    private String name = "张三";

    public Observer01() {}

    @Override
    public void getMessage(String s) {
        System.out.println(name + "收到王五的信息是:" + s);
    }

}
