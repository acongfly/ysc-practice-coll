package com.mapper;

import com.acongfly.bigdata.BigdataApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BigdataApplication.class)
public class TbPaymentTxnV2MapperTest {

    @Resource
    private TbPaymentTxnV2Mapper tbPaymentTxnV2Mapper;

    @Test
    public void insert() {

    }

    @Test
    public void delete() {
        tbPaymentTxnV2Mapper.deleteByPrimaryKey(10L);
    }

}