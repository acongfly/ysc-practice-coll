package com.acongfly.studyjava.designPatterns.proxy.lazyLoading;

/**
 * program: study
 * <p>
 * description: 模拟数据库连接耗时
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-08 10:16
 * <p>
 **/

public class DBQuery implements IDBQuery {

    public DBQuery() {
        try {
            Thread.sleep(1000);// 假设数据库连接等耗时操作
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String request() {
        return "request string";
    }
}
