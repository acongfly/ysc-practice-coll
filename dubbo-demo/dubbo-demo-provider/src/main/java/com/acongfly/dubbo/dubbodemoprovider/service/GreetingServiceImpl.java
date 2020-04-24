package com.acongfly.dubbo.dubbodemoprovider.service;

import com.acongfly.study.GreetingService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2020-04-03 13:46
 **/
@Service(version = "${demo.service.version}")
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHi(String name) {
        System.out.println("name=" + name);
        return "hi ," + name;
    }
}
