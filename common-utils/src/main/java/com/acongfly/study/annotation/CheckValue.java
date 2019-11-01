package com.acongfly.study.annotation;

import java.lang.annotation.*;

import javax.validation.groups.Default;

/**
 * description: 此注解是针对全局使用的数据校验操作
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-07-05
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Inherited
public @interface CheckValue {

    /**
     * 校验的接口标示
     *
     * @return
     */
    Class value() default Default.class;

    /**
     * 此方法用在多个payCode对应多个值的情况，这与上面的value方法中的值不会共存在，应保证两者值不能有共同值,两者都有值会先存value
     *
     * @return
     */
    Class[] groupValue() default {};

}
