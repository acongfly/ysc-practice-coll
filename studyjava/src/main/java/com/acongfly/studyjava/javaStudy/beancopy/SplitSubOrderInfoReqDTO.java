package com.acongfly.studyjava.javaStudy.beancopy;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * program: cashier-master
 * <p>
 * description: 拆子单信息请求VO
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2018-08-20 15:52
 * <p>
 **/
@Setter
@Getter
public class SplitSubOrderInfoReqDTO {

    /**
     * 需要结算到的商户的账户号(此账户收银台会开立)
     */
    @NotBlank
    private String partnerId;
    /**
     * 订单总金额 结算确认，实时金额必须是大于1毛钱即10分
     */
    @NotNull
    @Min(1L)
    private Long totalAmt;

    /**
     * 外部订单Id
     */
    @NotBlank
    @Length(max = 32)
    private String orderNo;

    /**
     * 支付渠道详情
     */
    @NotNull
    private List<PayChannelDetailInfo> payChannelDetailInfoList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
