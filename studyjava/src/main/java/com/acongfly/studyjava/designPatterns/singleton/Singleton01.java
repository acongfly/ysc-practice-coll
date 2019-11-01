package com.acongfly.studyjava.designPatterns.singleton;

/**
 * @author shicongyang
 * @Description: 单例设计模式学习01
 * @date 2018年4月16日 下午4:11:11
 */
public class Singleton01 {

    private static Singleton01 singleton01;

    private Singleton01() {}
    // 1.(懒汉写法，线程不安全版本) 这种在多线程的时候依旧有可能会生成两个实例
    // public static Singleton01 getInstance(){
    // if (singleton01 == null) {
    // singleton01 = new Singleton01();
    // }
    // return singleton01;
    // }

    // 2.(懒汉写法，线程安全版本) 但是缺点就是加锁是很耗性能的
    public static synchronized Singleton01 getInstance() {
        if (singleton01 == null) {
            singleton01 = new Singleton01();
        }
        return singleton01;
    }
}
