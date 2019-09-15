package com.acongfly.studyjava.controller;//package com.ushareit.fintech.payment.config;
//
//import cn.hutool.json.JSONUtil;
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
//import com.google.common.collect.Maps;
//import com.google.common.util.concurrent.RateLimiter;
//import com.ushareit.fintech.payment.common.entity.dto.BizResultDTO;
//import com.ushareit.fintech.payment.common.enums.RespCodeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//import java.util.concurrent.TimeUnit;
//
///**
// * description: 流控拦截器<p>
// * param:  <p>
// * return:  <p>
// * author: shicong yang <p>
// * date: 2019-03-25 <p>
// */
//@Component
//@Slf4j
//public class RateLimiterInterceptor extends HandlerInterceptorAdapter {
//
//    private enum LimitType {
//        DROP, NOMAL
//    }
//
//    private static final String REST_RESULT = JSONUtil.toJsonStr(BizResultDTO.buildBizResult(RespCodeEnum.LIMITING.getBizCode(), RespCodeEnum.LIMITING.getMsg(), Maps.newHashMap()));
//
//    private volatile LimitType limitType = LimitType.DROP;
//
//    private static final LoadingCache<Method, RateLimiter> RATE_LIMITER = CacheBuilder.newBuilder()
//            .build(new CacheLoader<Method, RateLimiter>() {
//                @Override
//                public RateLimiter load(Method key) {
//                    log.debug("hit limit method=" + key);
//                    /**
//                     * permitsPerSecond – 返回的RateLimiter的速率，意味着每秒有多少个许可变成有效。
//                     * warmupPeriod – 在这段时间内RateLimiter会增加它的速率，在抵达它的稳定速率或者最大速率之前
//                     * unit – 参数warmupPeriod 的时间单位
//                     */
//                    return RateLimiter.create(300, 500, TimeUnit.MILLISECONDS);
//                }
//            });
//
//    public RateLimiterInterceptor() {
//        /**/
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
//            return true;
//        }
//
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        RateLimiter rateLimiter = RATE_LIMITER.get(method);
//
//        if (limitType.equals(LimitType.DROP)) {
//            if (rateLimiter.tryAcquire()) {
//                return true;
//            }
//        } else {
//            rateLimiter.acquire();
//            return true;
//        }
//        log.info("hit limit process method=" + method);
//        // 设置格式为application/json
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        // 设置字符集为UTF-8
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        out.write(REST_RESULT);
//        out.flush();
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
//    }
//}
