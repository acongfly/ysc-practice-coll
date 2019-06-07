package com.acongfly.studyjava.designPatterns.singleton;

/**
 * @author shicongyang
 * @Description: 单例模式（静态内部类）   （建议使用）
 * 这种方式是Effective Java作者Josh Bloch 提倡的方式，
 * 它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象，可谓是很坚强的壁垒啊，
 * <p>
 * 这种也是可以按照需要进行创建实例
 * 只要是不调用singleton03.getInstance()就不会实例化
 * @date 2018年4月16日 下午4:23:23
 */
public class Singleton03 {

//    static {
//        System.out.println("this static one");
//    }

    private Singleton03() {
    }

    private static class SingletonHolder {
        private static final Singleton03 INSTANCE = new Singleton03();
//        static {
//            System.out.println("this static two");
//        }
    }

    public static final Singleton03 getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static void main(String[] args) {
        Singleton03.getInstance();
    }
}
