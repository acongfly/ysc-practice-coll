package com.mapper;

import lombok.Data;

@Data
public class Result {
    private String merchantId;
    private String tradeMasterNo;
    private String merchantBatchNo;
    private String orderId;
    private String tradeNo;
    private Byte status;
    private Long amount;
    private String currency;
    private String countryCode;
    private String payType;
    private String payeeUpi;
    private String payeeName;
}
