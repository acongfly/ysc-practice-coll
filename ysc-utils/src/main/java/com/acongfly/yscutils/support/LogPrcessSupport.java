package com.acongfly.yscutils.support;

import com.acongfly.yscutils.model.ReturnT;
import org.springframework.stereotype.Component;

/**
 * @program: ysc-practice-coll
 * @description: 日志处理操作支持接口
 * @author: shicong yang
 * @create: 2019-07-29 17:47
 **/
public abstract class LogPrcessSupport {

    /**
     * success
     */
    public static final ReturnT<String> SUCCESS = new ReturnT<String>(200, null);
    /**
     * fail
     */
    public static final ReturnT<String> FAIL = new ReturnT<String>(500, null);

    public LogPrcessSupport() {
    }

    public ReturnT<String> logSaveProcess(Object o) {
        return logSave(o);
    }

    public abstract ReturnT<String> logSave(Object o);

    /**
     * init handler, invoked when JobThread init
     */
    public void init() {
        // do something
    }


    /**
     * destroy handler, invoked when JobThread destroy
     */
    public void destroy() {
        // do something
    }

}
