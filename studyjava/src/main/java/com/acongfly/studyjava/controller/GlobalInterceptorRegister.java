package com.acongfly.studyjava.controller;// package com.ushareit.fintech.payment.config;

//
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
// import javax.annotation.Resource;
//
/// **
// * description: 拦截器注册器<p>
// * param: <p>
// * return: <p>
// * author: shicong yang <p>
// * date: 2019-03-25 <p>
// */
// @Component
// public class GlobalInterceptorRegister extends WebMvcConfigurerAdapter {
//
// @Resource
// private RateLimiterInterceptor rateLimiterInterceptor;
//
// @Override
// public void addInterceptors(InterceptorRegistry registry) {
// // 多个拦截器组成一个拦截器链
// registry.addInterceptor(rateLimiterInterceptor)
// //拦截的地址
// .addPathPatterns("/payment-service/**", "/query-service/**");
// //排除的地址
//// .excludePathPatterns("/query-service/getPayTypeAllList", "/query-service/getPayTypeByPCList",
// "/query-service/getPayTypeList", "/query-service/querySupportType");
// super.addInterceptors(registry);
// }
// }
//
