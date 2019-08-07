package com.acongfly.study.aspect;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.acongfly.study.annotation.LogSave;
import com.acongfly.study.common.CommonConstants;
import com.acongfly.study.model.LogInfo;
import com.acongfly.study.utils.GuavaCacheUtil;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LogAspect {


    private static Logger log = LoggerFactory.getLogger(LogAspect.class);


    @Pointcut("@annotation(com.acongfly.study.annotation.LogSave)")
    public void pointCut() {
    }

    @Before("pointCut()")
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
         * 缓存key 格式
         */
        String cacheKey = String.format(CommonConstants.LOG_CACHE_KEY, Thread.currentThread().getId(), simpleName, name);
        /**
         * 切面日志格式
         */
        log.info("{}.{} request params ={}", simpleName, name, reqMsg);
        log.info("local cache log key =[{}]", cacheKey);
        /**
         * 组装请求缓存信息
         */
        Map<String, Object> logMap = buildReqLogCacheInfo(name, simpleName, reqMsg, cacheKey);
        GuavaCacheUtil.putAll(logMap);

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

    /**
     * 组装请求缓存信息
     *
     * @param name       方法名称
     * @param simpleName 类名称
     * @param reqMsg     请求报文
     * @param cacheKey   缓存key
     * @return
     */
    private Map<String, Object> buildReqLogCacheInfo(String name, String simpleName, String reqMsg, String cacheKey) {
        Map<String, Object> logMap = Maps.newHashMap();
        LogInfo info = new LogInfo();
        info.setBizMethod(String.format(CommonConstants.BIZ_METHOD_FORMAT, simpleName, name));
        info.setMethodStartTime(System.currentTimeMillis());
        info.setReqMsg(reqMsg);
        info.setRespMsg("");
        logMap.put(cacheKey, info);
        return logMap;
    }


    @SuppressWarnings("All")
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        String simpleName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        LogInfo logInfo = (LogInfo) GuavaCacheUtil.getIfPresent(String.format(CommonConstants.LOG_CACHE_KEY, Thread.currentThread().getId(), simpleName, name));
        logInfo.setMethodEndTime(System.currentTimeMillis());
        if (!Objects.isNull(result)) {
            log.info("{}.{} response={}", simpleName, name, JSONUtil.toJsonStr(result));
            logInfo.setRespMsg(JSONUtil.toJsonStr(result));
        }
        boolean value = getControllerLogValue(joinPoint);
        log.info("{}.{} time consuming = [{}]ms", simpleName, name, logInfo.getMethodEndTime() - logInfo.getMethodStartTime());
        if (value) {
            /**
             * 异步记录日志操作 TODO
             */
            // 响应签名
        }

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
