package com.acongfly.studyjava.javaStudy.functionStudy;

import java.util.function.Function;

/**
 * description: apply基本应用
 * <p>
 * 字符串长度记录返回
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-04-09
 * <p>
 */
public class MyFunction implements Function<String, Integer> {

    @Override
    public Integer apply(String s) {
        return s.length();
    }
}