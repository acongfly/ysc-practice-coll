package com.acongfly.studyjava.thread.thread1;

/**
 * @author shicongyang
 * @ClassName: JoinTest
 * @Description: join方法学习 如果某线程在另外一个线程t上调用t.join（），此线程将被挂起，直到目标线程t结束才恢复（即t.isAlive()返回为假）
 *               对join()方法可以带上一个超时参数（单位可以是毫秒），这样如果目标线程在这段时间到期也没有结束的话，join()方法总能返回。
 *               对join（）的调用也可以中断，做法是在调用线程上调用interrupt()方法，这时候就需要try --catch语句了
 * @date 2018年5月13日 下午5:40:40
 */
public class JoinTest {

    public static void main(String[] args) {
        Sleeper zhangsan = new Sleeper("zhangsan", 2000);
        Sleeper lisi = new Sleeper("lisi", 2000);
        new Joiner("wangwu", zhangsan);
        new Joiner("sunliu", lisi);
        lisi.interrupt();

    }
}

class Sleeper extends Thread {

    private int duration;

    public Sleeper(String name, int sleepTime) {
        super(name); // 使用父类方法
        duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + "was interrupted" + "isTnterrupted()" + isInterrupted());
            return;
        }
        System.out.println(getName() + "has awakened");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println(getName() + "join completed");
    }
}