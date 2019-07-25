package com.acongfly.yscutils.annotation;

import com.acongfly.yscutils.utils.EnumValidtor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * description: 枚举校验注解<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-04-24 <p>
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidtor.class})
@Documented
public @interface EnumValid {
    String message() default "Enumeration is not in scope";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target();

    /**
     * 需要校验的数据的get方法,如：你需要校验枚举中code值，则传入getCode
     * 此方法区分大小写，且必填
     *
     * @return
     */
    String enumMethod();
}