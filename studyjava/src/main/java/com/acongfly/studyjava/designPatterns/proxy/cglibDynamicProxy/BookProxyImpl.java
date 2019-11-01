package com.acongfly.studyjava.designPatterns.proxy.cglibDynamicProxy;

// 该类并没有申明 BookProxy 接口
public class BookProxyImpl {
    public void addBook() {
        System.out.println("增加图书的普通方法...");
    }
}