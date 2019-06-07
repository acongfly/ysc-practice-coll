package com.acongfly.studyjava.designPatterns.proxy.lazyLoading;

/**
 * program: study<p>
 * description: <p>
 * author: shicong yang<p>
 * createDate: 2019-01-08 10:48<p>
 **/

public class TestMain {
    public static void main(String[] args) {
        IDBQuery q = new DBQueryProxy();     //使用代理
        String request = q.request();//在真正使用时候才进行创建
        System.out.println(request);
    }
}
