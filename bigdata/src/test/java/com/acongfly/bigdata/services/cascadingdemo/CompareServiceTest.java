package com.acongfly.bigdata.services.cascadingdemo;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.bigdata.BigdataApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BigdataApplication.class)
public class CompareServiceTest {

    @Resource
    private CompareService compareService;

    @Test
    public void compareForCsv() {
        compareService.compareForCsv();
    }

}