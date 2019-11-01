package com.acongfly.studyjava.designPatterns.strategy02;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @program: fintech-payment
 * @description: upi账户信息（JSON中字段）
 * @author: shicong yang
 * @create: 2019-05-09 14:27
 **/
@Data
public class UpiAccinfoJson implements AbstractAccInfoBaseSupport {

    private static final long serialVersionUID = 1299178874931786738L;

    @Override
    public String buildAccInfo(BaseAccInfoJson baseAccInfoJson) {
        UpiAccInfo upiAccInfo = new UpiAccInfo();
        upiAccInfo.setPayeeAccount(baseAccInfoJson.getPayeeAccount());
        return JSONUtil.toJsonStr(upiAccInfo);
    }

    @Override
    public BaseAccInfoJson readAccInfo(String json) {
        UpiAccInfo upiAccInfo = JSONUtil.toBean(json, UpiAccInfo.class);
        BaseAccInfoJson baseAccInfoJson = new BaseAccInfoJson();
        baseAccInfoJson.setPayeeAccount(upiAccInfo.getPayeeAccount());
        return baseAccInfoJson;
    }

    @Data
    private static class UpiAccInfo {
        /**
         * Upi账户
         */
        private String payeeAccount;
    }
}
