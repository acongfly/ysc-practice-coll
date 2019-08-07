package com.acongfly.study.utils;

import com.acongfly.study.annotation.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description: 枚举验证类<p>
 * param:  <p>
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-04-11 <p>
 */
public class EnumValidtor implements ConstraintValidator<EnumValid, Object> {

    Class<?>[] cls; //枚举类
    private String enumMethod;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        cls = constraintAnnotation.target();
        enumMethod = constraintAnnotation.enumMethod();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (cls.length > 0) {
            for (Class<?> cl : cls) {
                try {
                    if (cl.isEnum()) {
                        //枚举类验证
                        Object[] objs = cl.getEnumConstants();
                        Method method = cl.getMethod(enumMethod);
                        for (Object obj : objs) {
                            Object code = method.invoke(obj);
                            if (value.equals(code)) {
                                return true;
                            }
                        }
                    }
                } catch (NoSuchMethodException e) {
                    //不影响业务 不抛异常
                    return false;
                } catch (IllegalAccessException e) {
                    //不影响业务 不抛异常
                    return false;
                } catch (InvocationTargetException e) {
                    //不影响业务 不抛异常
                    return false;
                }
            }
        }
        return false;
    }
}