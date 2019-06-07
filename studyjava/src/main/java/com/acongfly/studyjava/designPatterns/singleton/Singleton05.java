package com.acongfly.studyjava.designPatterns.singleton;

/**
 * 枚举模式：最安全
 */
public class Singleton05 {

    // 私有构造函数
    private Singleton05() {

    }

    public static Singleton05 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private Singleton05 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton() {
            singleton = new Singleton05();
        }

        public Singleton05 getInstance() {
            return singleton;
        }
    }

    public static void main(String[] args) {
        Singleton05.getInstance();
    }
}