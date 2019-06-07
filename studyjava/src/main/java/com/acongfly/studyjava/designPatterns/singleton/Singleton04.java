package com.acongfly.studyjava.designPatterns.singleton;

/**
 * @author shicongyang
 * @Description: 单例模式（双重校验锁）  可以校验出当实例被创建后，就不会创建实例
 * 缺点是：用双重if判断，复杂，容易出错。
 * @date 2018年4月16日 下午4:34:53
 */
public class Singleton04 {

    private volatile static Singleton04 singleton04;

    private Singleton04() {
    }

    public static Singleton04 getInstance() {
        if (singleton04 == null) {
            synchronized (Singleton04.class) {
                if (singleton04 == null) {
                    singleton04 = new Singleton04();
                }
            }
        }
        return singleton04;
    }

}
