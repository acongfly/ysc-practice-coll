package com.acongfly.studyjava.utils;

/**
 * description: 解析表达式异常<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-05-10 <p>
 */
public class ParseExpressionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 附带日志消息和异常信息组成的构造方法.
     *
     * @param msg 日志消息
     * @param t   Throwable对象
     */
    public ParseExpressionException(String msg, Throwable t) {
        super(msg, t);
    }


}