package com.acongfly.yscutils.support;

import com.acongfly.yscutils.annotation.LogSave;
import com.acongfly.yscutils.model.ReturnT;
import org.springframework.stereotype.Component;

/**
 * @program: ysc-practice-coll
 * @description: 支持接口example
 * @author: shicong yang
 * @create: 2019-07-31 11:18
 **/
//@Component
public class LogProcessSupportImpl extends LogPrcessSupport {
    @Override
    public ReturnT<String> logSave(Object o) {
        System.out.println("9999999999999999999999999999");
        return ReturnT.SUCCESS;
    }
}
