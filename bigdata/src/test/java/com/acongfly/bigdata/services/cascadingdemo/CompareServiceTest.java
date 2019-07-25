package com.acongfly.bigdata.services.cascadingdemo;

import cascading.CascadingTestCase;
import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.ConcreteCall;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleListCollector;
import com.acongfly.bigdata.BigdataApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Map;

import static org.junit.Assert.*;

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