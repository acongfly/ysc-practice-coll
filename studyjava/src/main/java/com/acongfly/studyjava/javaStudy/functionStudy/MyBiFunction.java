package com.acongfly.studyjava.javaStudy.functionStudy;

import java.util.function.BiFunction;

/**
 * description: 返回两个字符串的连接，BiFunction与Function的不同就是传入两个参数，依旧返回一个值。<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-04-09 <p>
 */
public class MyBiFunction implements BiFunction<String, String, String> {
    @Override
    public String apply(String s, String s2) {
        return s + ";" + s2;
    }


    private static String hello = "Nice to meet you";
    private static String name = "my name is huohuo";

    public static void main(String[] args) {
        MyFunction myFunction = new MyFunction();
        MyBiFunction biFunction = new MyBiFunction();
        int num = myFunction.apply(hello);
        String valueBi = biFunction.apply(hello, name);
        //hello长度返回
        System.out.println(num);
        //语句整合返回
        System.out.println(valueBi);
    }
}