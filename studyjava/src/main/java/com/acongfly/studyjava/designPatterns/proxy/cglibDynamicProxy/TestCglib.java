package com.acongfly.studyjava.designPatterns.proxy.cglibDynamicProxy;

/**
 * program: study
 * <p>
 * description: 动态代理测试
 * 参考：https://www.ibm.com/developerworks/cn/java/j-lo-proxy-pattern/index.html?mhq=%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F&mhsrc=ibmsearch_d
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-08 12:18
 * <p>
 **/

public class TestCglib {
    public static void main(String[] args) {
        BookProxyLib cglib = new BookProxyLib();
        BookProxyImpl bookCglib = (BookProxyImpl)cglib.getInstance(new BookProxyImpl());
        bookCglib.addBook();
    }
}
