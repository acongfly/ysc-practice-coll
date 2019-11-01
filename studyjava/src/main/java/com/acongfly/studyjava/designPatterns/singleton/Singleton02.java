package com.acongfly.studyjava.designPatterns.singleton;

/**
 * @author shicongyang
 * @Description: 单例模式的饿汉模式 （建议使用）初试化静态的instance创建一次。如果我们在Singleton类里面写一个静态的方法不需要创建实例，它仍然会早早的创建一次实例。而降低内存的使用率 没有lazy
 *               loading的效果，从而降低内存的使用率
 * @date 2018年4月16日 下午4:20:27
 */
public class Singleton02 {
    private Singleton02() {}

    private static Singleton02 singleton02 = new Singleton02();

    public static Singleton02 getInstance() {
        return singleton02;
    }
}
