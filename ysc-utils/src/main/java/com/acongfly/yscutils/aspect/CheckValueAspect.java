package com.acongfly.yscutils.aspect;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.acongfly.yscutils.annotation.CheckValue;
import com.acongfly.yscutils.enums.ErrorEnum;
import com.acongfly.yscutils.exception.CheckException;
import com.acongfly.yscutils.utils.ValidationResult;
import com.acongfly.yscutils.utils.ValidationUtils;
import com.acongfly.yscutils.vo.BaseRequestVO;
import com.google.common.collect.Sets;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * description: checkValue 个性化定制校验 param: return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-03-25
 * <p>
 */
@Component
@Aspect
@Slf4j
public class CheckValueAspect {

    @Around("@annotation(com.acongfly.yscutils.annotation.CheckValue)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        BaseRequestVO req = null;
        Object[] args = point.getArgs();
        Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
        if (args != null && args.length > 0) {
            if (args[0] instanceof BaseRequestVO) {
                req = (BaseRequestVO)args[0];
            }
        }
        if (req == null) {
            throw new CheckException(ErrorEnum.ERROR_REQ_NULL.getCode(), ErrorEnum.ERROR_REQ_NULL.getMessage());
        }
        String simpleName = point.getSignature().getDeclaringType().getSimpleName();

        Set<Class> checkValueClass = getCheckValueClass(point);
        for (Class clazz : checkValueClass) {
            ValidationResult result = ValidationUtils.validateByGroup(req, clazz);
            if (result.isHasErrors()) {
                log.error("{}.{} Check in the parameters, the data is incorrect[{}]", simpleName, method.getName(),
                    result);
                throw new CheckException(ErrorEnum.ERROR_CHECK_VALIDATE.getCode(), result.getErrorMsg().toString());
            }
        }
        return point.proceed();
    }

    /**
     * description: 获取注解值
     * <p>
     * param: [joinPoint]
     * <p>
     * return: boolean
     * <p>
     * author: shicong yang
     * <p>
     * date: 2019-04-22
     * <p>
     */
    private Set<Class> getCheckValueClass(JoinPoint joinPoint) {
        // 获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        // 获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        // 得到方法的参数的类型
        Class[] argClass = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
        Set<Class> checkClass = Sets.newHashSet();
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@ControllerLog注解
            if (method.isAnnotationPresent(CheckValue.class)) {
                CheckValue annotation = method.getAnnotation(CheckValue.class);
                // 取出注解中的数据源名
                Class value = annotation.value();
                checkClass.add(value);
                Class[] classes = annotation.groupValue();
                if (ArrayUtil.isNotEmpty(classes)) {
                    for (int i = 0; i < classes.length; i++) {
                        checkClass.add(classes[i]);
                        log.info("getCheckValueClass checkValue value={}", classes[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkClass;
    }
}
