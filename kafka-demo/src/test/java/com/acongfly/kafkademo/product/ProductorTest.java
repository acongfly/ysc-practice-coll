package com.acongfly.kafkademo.product;

import com.acongfly.kafkademo.KafkaDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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