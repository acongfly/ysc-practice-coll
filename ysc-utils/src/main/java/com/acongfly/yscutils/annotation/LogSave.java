package com.acongfly.yscutils.annotation;


import java.lang.annotation.*;

/**
 * description: 日志保存操作，用于service和feign相关请求操作<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-04-22 <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface LogSave {


    /**
     * description: 是否记录到数据库表中，默认是记录
     * 如果为false，就只打印日志，不记录数据库中<p>
     * param: [] <p>
     * return: boolean <p>
     * author: shicong yang <p>
     * date: 2019-04-22 <p>
     */
    boolean value() default true;

}
