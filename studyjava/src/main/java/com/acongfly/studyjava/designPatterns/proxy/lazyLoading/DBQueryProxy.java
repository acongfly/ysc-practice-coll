package com.acongfly.studyjava.designPatterns.proxy.lazyLoading;

/**
 * program: study
 * <p>
 * description: 查询代理
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-01-08 10:31
 * <p>
 **/

public class DBQueryProxy implements IDBQuery {

    private DBQuery real = null;

    @Override
    public String request() {
        if (real == null) {
            real = new DBQuery();
        }
        // 在多线程环境下，这里返回一个虚假类，类似future模式
        return real.request();
    }
}
