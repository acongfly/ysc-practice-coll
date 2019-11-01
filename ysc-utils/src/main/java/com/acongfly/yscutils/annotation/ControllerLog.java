package com.acongfly.yscutils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: controller层切面注解
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-04-22
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ControllerLog {

    /**
     * description: 是否记录到数据库表中，默认是记录 如果为false，就只打印日志
     * <p>
     * param: []
     * <p>
     * return: boolean
     * <p>
     * author: shicong yang
     * <p>
     * date: 2019-04-22
     * <p>
     */
    boolean value() default true;
}
