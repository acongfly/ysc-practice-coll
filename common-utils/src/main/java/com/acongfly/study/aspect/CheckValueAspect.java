package com.acongfly.study.aspect;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.acongfly.study.annotation.CheckValue;
import com.acongfly.study.enums.ErrorEnum;
import com.acongfly.study.exception.CheckException;
import com.acongfly.study.utils.ValidationResult;
import com.acongfly.study.utils.ValidationUtils;
import com.acongfly.study.vo.BaseCheckTag;
import com.google.common.collect.Sets;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * description: checkValue 个性化定制校验
 * param:
 * return:  <p>
 * author: shicong yang <p>
 * date: 2019-03-25 <p>
 */
@Aspect
@Component
public class CheckValueAspect {
    private static Logger log = LoggerFactory.getLogger(CheckValueAspect.class);


    @Around("@annotation(com.acongfly.study.annotation.CheckValue)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        BaseCheckTag req = null;
        Object[] args = point.getArgs();
        Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
        if (args != null && args.length > 0) {
            if (args[0] instanceof BaseCheckTag) {
                req = (BaseCheckTag) args[0];
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
                log.error("{}.{} Check in the parameters, the data is incorrect[{}]", simpleName, method.getName(), result);
                JSONObject reqJsonObject = JSONUtil.parseFromMap(result.getErrorMsg());
                String reqMsg = JSONUtil.toJsonStr(reqJsonObject);
                throw new CheckException(ErrorEnum.ERROR_CHECK_VALIDATE.getCode(), reqMsg);
            }
        }
        return point.proceed();
    }

    /**
     * description: 获取注解值<p>
     * param: [joinPoint] <p>
     * return: boolean <p>
     * author: shicong yang <p>
     * date: 2019-04-22 <p>
     */
    private Set<Class> getCheckValueClass(JoinPoint joinPoint) {
        //获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Set<Class> checkClass = Sets.newHashSet();
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@CheckValue注解
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
