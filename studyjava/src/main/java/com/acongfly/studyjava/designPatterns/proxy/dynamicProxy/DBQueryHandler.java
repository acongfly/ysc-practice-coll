package com.acongfly.studyjava.designPatterns.proxy.dynamicProxy;

import com.acongfly.studyjava.designPatterns.proxy.lazyLoading.DBQuery;
import com.acongfly.studyjava.designPatterns.proxy.lazyLoading.IDBQuery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * program: study<p>
 * description: 动态代理实现<p>
 * author: shicong yang<p>
 * createDate: 2019-01-08 11:37<p>
 **/

public class DBQueryHandler implements InvocationHandler {

    IDBQuery realQuery = null;      //定义主题接口

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //第一次调用，生成真实主题
        if (method.getName().equals("request")) {
            if (realQuery == null) {
                realQuery = new DBQuery();
            }
            return realQuery.request();
        }
        return null;
    }

    public static IDBQuery createProxy() {
        IDBQuery proxy = (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IDBQuery.class}, new DBQueryHandler());
        return proxy;
    }
}
