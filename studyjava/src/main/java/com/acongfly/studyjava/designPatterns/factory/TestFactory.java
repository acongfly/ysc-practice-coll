package com.acongfly.studyjava.designPatterns.factory;

public class TestFactory {
    public static void main(String[] args) {
        Car carInstance = Factory.getCarInstance("BMW");
        if (carInstance != null) {
            carInstance.run();
            carInstance.stop();
        } else {
            System.out.println("类型不匹配");
        }
    }

}
