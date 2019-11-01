package com.acongfly.yscutils.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.acongfly.yscutils.annotation.ControllerLog;
import com.acongfly.yscutils.enums.ErrorEnum;
import com.acongfly.yscutils.exception.CheckException;
import com.acongfly.yscutils.vo.BaseRequestVO;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * description: Controller层APO组件，完成接口层AOP逻辑 *
 * <ol>
 * *
 * <li>完成API层请求验签（认证）逻辑</li> *
 * <li>完成API层响应签名逻辑</li> *
 * </ol>
 * <p>
 * param: return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-03-25
 * <p>
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Around("@annotation(com.acongfly.yscutils.annotation.ControllerLog)")
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

        // TODO 数据校验

        // TODO 验签

        // TODO: 授权逻辑，检查当前调用方是否有权限访问服务资源
        // TODO: Metric进程内监控数据收集
        // TODO: 请求响应日志添加
        return point.proceed();
    }

    /**
     * 入参
     */
    @Before("@annotation(com.acongfly.yscutils.annotation.ControllerLog)")
    public void before(JoinPoint joinPoint) {
        log.info("{}.{} request params = {}", joinPoint.getSignature().getDeclaringType().getSimpleName(),
            joinPoint.getSignature().getName(), JSONUtil.toJsonStr(joinPoint.getArgs()));
    }

    /**
     * 返回值
     */
    @SuppressWarnings("All")
    @AfterReturning(pointcut = "@annotation(com.acongfly.yscutils.annotation.ControllerLog)", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        String simpleName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        if (!Objects.isNull(result)) {
            log.info("{}.{} response params = {}", simpleName, name, JSONUtil.toJsonStr(result));
        }
        // boolean value = getControllerLogValue(joinPoint);
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
    private boolean getControllerLogValue(JoinPoint joinPoint) {
        // 获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        // 获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        // 得到方法的参数的类型
        Class[] argClass = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
        boolean value = false;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@ControllerLog注解
            if (method.isAnnotationPresent(ControllerLog.class)) {
                ControllerLog annotation = method.getAnnotation(ControllerLog.class);
                // 取出注解中的数据源名
                value = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
