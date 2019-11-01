package com.acongfly.studyjava.controller;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.studyjava.javaStudy.other.FileTrans;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceTest {

    @Resource
    private TestService testService;

    @Test
    public void fileProcess() {
        FileTrans fileTran = new FileTrans();
        fileTran.setMerId("1234567");
        testService.fileProcess(fileTran);
    }
}