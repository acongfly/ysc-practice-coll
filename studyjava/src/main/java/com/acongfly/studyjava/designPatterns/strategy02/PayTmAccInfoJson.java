package com.acongfly.studyjava.designPatterns.strategy02;

import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @program: fintech-payment
 * @description: payTm账号信息(JSON)
 * @author: shicong yang
 * @create: 2019-05-09 15:07
 **/
@Data
public class PayTmAccInfoJson implements AbstractAccInfoBaseSupport {
    private static final long serialVersionUID = 4886533430292022429L;

    @Override
    public String buildAccInfo(BaseAccInfoJson baseAccInfoJson) {

        PayTmAccInfo payTmAccInfo = new PayTmAccInfo();
        payTmAccInfo.setPayeeAccount(baseAccInfoJson.getPayeeAccount());
        payTmAccInfo.setPayTmAppliedToNewUsers(baseAccInfoJson.getPayTmAppliedToNewUsers());
        return JSONUtil.toJsonStr(payTmAccInfo);
    }

    @Override
    public BaseAccInfoJson readAccInfo(String json) {
        BaseAccInfoJson baseAccInfoJson = new BaseAccInfoJson();
        PayTmAccInfo payTmAccInfo = JSONUtil.toBean(json, PayTmAccInfo.class);
        baseAccInfoJson.setPayeeAccount(payTmAccInfo.getPayeeAccount());
        baseAccInfoJson.setPayTmAppliedToNewUsers(payTmAccInfo.getPayTmAppliedToNewUsers());
        return baseAccInfoJson;
    }

    @Data
    private static class PayTmAccInfo {
        /**
         * payTm账户信息
         */
        private String payeeAccount;

        /**
         * payTm是否开立新账户，默认是N。Y/N
         */
        private String payTmAppliedToNewUsers;
    }
}
