package com.acongfly.dubbo.dubbodemoconsumer.controller;

import com.acongfly.study.GreetingService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2020-04-03 15:46
 **/
@RestController
public class Consumer {

    @Reference(version = "${demo.service.version}", url = "${demo.service.url}")
    private GreetingService greetingService;

    @Scheduled(fixedDelay = 1000)
    public void dubboComsumer() {
        greetingService.sayHi(System.currentTimeMillis() + "");
    }
}
