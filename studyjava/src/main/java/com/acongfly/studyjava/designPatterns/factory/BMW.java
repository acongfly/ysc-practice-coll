package com.acongfly.studyjava.designPatterns.factory;

public class BMW implements Car {

    @Override
    public void run() {
        System.out.println("BMW跑起来了");
    }

    @Override
    public void stop() {
        System.out.println("BMW停下来了");
    }

}
