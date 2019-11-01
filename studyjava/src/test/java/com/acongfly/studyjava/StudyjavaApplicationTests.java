package com.acongfly.studyjava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.studyjava.spring.AsyncStudy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyjavaApplicationTests {

    @Autowired
    private AsyncStudy asyncStudy;
    @Value("#{'${table.name}'.split(',')}")
    private String[] tableName;

    @Test
    public void contextLoads() {
        // Map<String, String> map = new HashMap<>();
        // map.put("param", "{code=200,message=success}");
        //// HttpClientUtil.post("http://localhost:8001/getHttpInfo",map);
        // HttpUtil.getInstance().doHttpPost("http://localhost:8001/getHttpInfo", map);
    }

    @Test
    public void testAsync() throws InterruptedException, ExecutionException {
        Future<String> future = null;
        System.out.println("你不爱我了么?");
        future = asyncStudy.sayHello1();
        System.out.println("你竟无话可说, 我们分手吧。。。");
        Thread.sleep(3 * 1000);// 不让主进程过早结束
        System.out.println(future.get());
    }

    @Test
    public void testTable() {
        System.out.println(tableName);
    }

}
