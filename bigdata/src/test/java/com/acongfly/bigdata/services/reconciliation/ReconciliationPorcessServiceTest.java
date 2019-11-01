package com.acongfly.bigdata.services.reconciliation;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acongfly.bigdata.BigdataApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BigdataApplication.class)
public class ReconciliationPorcessServiceTest {

    @Resource
    private ReconciliationPorcessService reconciliationPorcessService;

    @Test
    public void creatTxnCsv() {
        reconciliationPorcessService.creatTxnCsv();
    }

    @Test
    public void sumProcess() {
        try {
            reconciliationPorcessService.sumProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}