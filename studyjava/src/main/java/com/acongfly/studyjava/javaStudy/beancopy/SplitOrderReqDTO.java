package com.acongfly.studyjava.javaStudy.beancopy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * program: cashier-master<p>
 * description: 拆单请求DTO<p>
 * author: shicong yang<p>
 * createDate: 2018-10-24 14:15<p>
 **/
@Setter
@Getter
public class SplitOrderReqDTO {

    /**
     * 主单的交易ID
     */
    @NotBlank
    @Length(max = 32)
    private String transactionId;

    /**
     * 子单相关信息 List
     */
    @NotNull
    List<SplitSubOrderInfoReqDTO> subOrderInfoList;
}
