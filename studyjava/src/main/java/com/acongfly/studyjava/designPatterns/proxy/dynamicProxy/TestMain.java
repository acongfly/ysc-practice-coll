package com.acongfly.studyjava.designPatterns.proxy.dynamicProxy;

import com.acongfly.studyjava.designPatterns.proxy.lazyLoading.IDBQuery;

/**
 * program: study
 * <p>
 * description:
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-08 10:48
 * <p>
 **/

public class TestMain {
    public static void main(String[] args) {
        IDBQuery proxy = DBQueryHandler.createProxy();
        System.out.println(proxy.request());
    }
}
