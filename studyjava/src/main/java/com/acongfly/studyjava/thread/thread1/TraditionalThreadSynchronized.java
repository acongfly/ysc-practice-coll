package com.acongfly.studyjava.thread.thread1;

/**
 * @author shicongyang
 * @ClassName: TraditionalThreadSynchronized
 * @Description: 传统的线程同步互斥
 * @date 2018年4月21日 下午7:32:33
 */
public class TraditionalThreadSynchronized {

    public static void main(String[] args) {
        new TraditionalThreadSynchronized().init();
    }

    private void init() {
        Output output = new Output();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.outputer("zhangsan");
                }
            }
        }).start();


        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.outputer("lisi");
                }
            }
        }).start();
    }


    class Output {
        public void outputer(String name) {
            int len = name.length();
            synchronized (this) {                    //必须是同一个对象加锁，此关键字才起作用
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
            }
            System.out.println();
        }
    }
}
