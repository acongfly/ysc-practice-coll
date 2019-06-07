package com.acongfly.studyjava.javaStudy.beancopy;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * program: cashier-master<p>
 * description: 支付渠道信息详情<p>
 * author: shicong yang<p>
 * createDate: 2018-10-24 14:33<p>
 **/
@Setter
@Getter
public class PayChannelDetailInfo {

    /**
     * 支付渠道
     */
    @NotNull
    private Integer payChannel;

    /**
     * 渠道金额
     */
    @NotNull
    @Min(1L)
    private Long channelAmt;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
