package com.acongfly.studyjava.thread.thread1;

import java.util.Random;

/**
 * @author shicongyang
 * @ClassName: TreadScopeShareData
 * @Description: threadLocal学习
 * @date 2018年4月22日 上午11:14:13
 */
public class ThreadLocalTest {

    //注释掉的代码是一种比较笨的方法实现，未注释的使用单例的方式进行代码优化，使得threadLocal在实体类封装，让使用者不关心threadLocal
//	private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
//	private static ThreadLocal<MyThreadLocalTest> myThreadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "data=" + data);
//					threadLocal.set(data);
//					MyThreadLocalTest myThreadLocalTest = new MyThreadLocalTest();
//					myThreadLocalTest.setAge(data);
//					myThreadLocalTest.setName(Thread.currentThread().getName());
//					myThreadLocal.set(myThreadLocalTest);
                    MyThreadLocalTest.geThreadLocalTest().setAge(data);
                    MyThreadLocalTest.geThreadLocalTest().setName(Thread.currentThread().getName());
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
//			int data = threadLocal.get();
//			System.out.println("A"+Thread.currentThread().getName()+"data="+data);
//			MyThreadLocalTest myThreadLocalTest = myThreadLocal.get();
            MyThreadLocalTest myThreadLocalTest = MyThreadLocalTest.geThreadLocalTest();
            System.out.println(myThreadLocalTest.getName() + "|||A|||" + myThreadLocalTest.getAge());
        }
    }

    static class B {
        public void get() {
//			int data = threadLocal.get();
//			System.out.println("B"+Thread.currentThread().getName()+"data="+data);
//			MyThreadLocalTest myThreadLocalTest = myThreadLocal.get();
            MyThreadLocalTest myThreadLocalTest = MyThreadLocalTest.geThreadLocalTest();
            System.out.println(myThreadLocalTest.getName() + "|||B|||" + myThreadLocalTest.getAge());
        }
    }

}


class MyThreadLocalTest {
    //单例写法(饿汉写法)
    private MyThreadLocalTest() {
    }

    private static ThreadLocal<MyThreadLocalTest> map = new ThreadLocal<>();

    //	private static  MyThreadLocalTest myThreadLocalTest;
    public static /*synchronized*/ MyThreadLocalTest geThreadLocalTest() {
        MyThreadLocalTest myThreadLocalTest = map.get();
        if (myThreadLocalTest == null) {
            myThreadLocalTest = new MyThreadLocalTest();
            map.set(myThreadLocalTest);
        }
        return myThreadLocalTest;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}