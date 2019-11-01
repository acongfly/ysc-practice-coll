package com.acongfly.droolsdemo;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.droolsdemo.model.User;

/**
 * https://mp.weixin.qq.com/s/jqaMsokYNwDsQTlsRpg8nA
 * https://zhixiang.org.cn/2018/11/24/rools%E8%A7%84%E5%88%99%E5%BC%95%E6%93%8E%E5%85%A5%E9%97%A8%E6%8C%87%E5%8D%97%EF%BC%88%E4%BA%8C%EF%BC%89/
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsDemoApplicationTests {

    @Autowired
    KieSession kieSession;

    @Test
    public void contextLoads() {}

    @Test
    public void testHelloWord() {
        kieSession.fireAllRules();
    }

    /**
     * 执行所有规则
     */
    @Test
    public void testUser() {
        User user = new User("张三", 20);
        kieSession.insert(user);
        kieSession.fireAllRules();
        System.err.println("规则执行完毕后张三变为了：" + user.getName());
    }

    /**
     * 执行某一规则
     */
    @Test
    public void testOneRule() {
        User user = new User("张三", 20);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("users"));
    }

    /**
     * contains用于判断对象的某个字段是否包含另外一个对象
     */
    @Test
    public void testContains() {
        String name = "张三";
        User user = new User("张三", 18);
        kieSession.insert(name);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("contains"));
    }

    /**
     * memberOf用于判断对象的某个字段是否存在一个集合中
     */
    @Test
    public void testMemberOf() {
        List list = new ArrayList();
        list.add("张三");
        list.add("李四");
        User user = new User("李四", 18);
        kieSession.insert(list);
        kieSession.insert(user);
        kieSession.fireAllRules(new RuleNameEndsWithAgendaFilter("memberOf"));
    }

}
