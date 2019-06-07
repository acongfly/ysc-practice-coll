package com.acongfly.studyjava.designPatterns.factory;

/**
 * @author shicongyang
 * @ClassName: factory
 * @Description: 工厂类
 * @date 2018年4月16日 下午11:59:37
 */
public class Factory {

    public static Car getCarInstance(String type) {
        Car car = null;
        if ("BMW".equals(type)) {
            car = new BMW();
        }
        if ("Ford".equals(type)) {
            car = new Ford();
        }
        return car;
    }

}
