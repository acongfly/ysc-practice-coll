package com.acongfly.studyjava.controller;

import com.acongfly.studyjava.javaStudy.other.FileTrans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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