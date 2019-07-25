package com.acongfly.bigdata.services.reconciliation;

import com.acongfly.bigdata.BigdataApplication;
import com.acongfly.bigdata.services.reconciliation.ReconciliationPorcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.IOException;

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