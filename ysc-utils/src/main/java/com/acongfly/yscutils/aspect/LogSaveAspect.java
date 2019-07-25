package com.acongfly.yscutils.aspect;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.acongfly.yscutils.annotation.LogSave;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @program: fintech-payment
 * @description: 自定义注解，记录需要保存日志的相关方法操作
 * @author: shicong yang
 * @create: 2019-04-19 22:01
 **/
@Aspect
@Component
@Slf4j
public class LogSaveAspect {


    @Before("@annotation(com.acongfly.yscutils.annotation.LogSave)")
    public void beforeLogSave(JoinPoint point) {
        String name = point.getSignature().getName();

        Map<String, Object> paramMap = buildReqMsgInfo(point);
        /**
         * 请求msg 转为jsonObject 方便后面处理
         */
        JSONObject reqJsonObject = JSONUtil.parseFromMap(paramMap);
        String simpleName = point.getSignature().getDeclaringType().getSimpleName();        //类名称
        String reqMsg = JSONUtil.toJsonStr(reqJsonObject);
        /**
         * 设置初始请求流水号
         */
        String requestNo = String.valueOf(System.currentTimeMillis());
        /**
         * 切面日志格式
         */
        log.info("{}.{} requestNo = [{}],request params ={}", simpleName, name, requestNo, reqMsg);

    }

    /**
     * 组装请求参数信息
     *
     * @param point
     * @return
     */
    private Map<String, Object> buildReqMsgInfo(JoinPoint point) {
        Object[] paramValues = point.getArgs();
        String[] paramNames = ((CodeSignature) point.getSignature()).getParameterNames();
        Map<String, Object> paramMap = Maps.newHashMap();
        for (int i = 0; i < paramNames.length; i++) {
            paramMap.put(paramNames[i], paramValues[i]);
        }
        return paramMap;
    }


    @SuppressWarnings("All")
    @AfterReturning(pointcut = "@annotation(com.acongfly.yscutils.annotation.LogSave)", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        String simpleName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        if (!Objects.isNull(result)) {
            log.info("{}.{} response={}", simpleName, name, JSONUtil.toJsonStr(result));
        }
        boolean value = getControllerLogValue(joinPoint);
    }


    /**
     * description: 获取注解值<p>
     * param: [joinPoint] <p>
     * return: boolean <p>
     * author: shicong yang <p>
     * date: 2019-04-22 <p>
     */
    private boolean getControllerLogValue(JoinPoint joinPoint) {
        //获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        boolean value = false;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@Logsave注解
            if (method.isAnnotationPresent(LogSave.class)) {
                LogSave annotation = method.getAnnotation(LogSave.class);
                // 取出注解中的数据源名
                value = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
