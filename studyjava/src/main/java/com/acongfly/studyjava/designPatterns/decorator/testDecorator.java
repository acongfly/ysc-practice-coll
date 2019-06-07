package com.acongfly.studyjava.designPatterns.decorator;

/**
 * @author shicongyang
 * @ClassName: testDecorator
 * @Description:装饰者模式测试
 * @date 2018年4月16日 下午9:42:05
 */
public class testDecorator {

    public static void main(String[] args) {
        Food food = new Bread(new Vegetable(new Cream(new Food("火腿"))));
        System.out.println(food.make());
    }

}
