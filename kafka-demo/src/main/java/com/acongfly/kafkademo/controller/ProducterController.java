package com.acongfly.kafkademo.controller;

import com.acongfly.kafkademo.product.Productor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: ysc-practice-coll
 * @description: 生产者
 * @author: shicong yang
 * @create: 2019-07-05 16:35
 **/
@RestController
public class ProducterController {

    @Resource
    private Productor productor;

    @GetMapping("/product")
    public void product() {
        productor.product();
    }
}
