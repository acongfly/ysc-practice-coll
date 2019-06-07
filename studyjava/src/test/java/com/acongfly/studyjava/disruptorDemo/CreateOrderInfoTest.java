package com.acongfly.studyjava.disruptorDemo;

import com.acongfly.studyjava.StudyjavaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudyjavaApplication.class)
public class CreateOrderInfoTest {

    @Resource
    private CreateOrderInfo createOrderInfo;

    @Test
    public void createOrder() {
        for (int i = 0; i < 1000; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId("order" + i);
            orderInfo.setRequestNo(System.currentTimeMillis() + "");
            orderInfo.setAmt(0L);
            orderInfo.setOrderType(0);
            createOrderInfo.createOrder(orderInfo);
        }
    }
}