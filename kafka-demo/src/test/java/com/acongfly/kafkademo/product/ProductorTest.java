package com.acongfly.kafkademo.product;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.kafkademo.KafkaDemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaDemoApplication.class)
public class ProductorTest {

    @Resource
    private Productor productor;

    @Test
    public void product() {
        productor.product();
    }
}