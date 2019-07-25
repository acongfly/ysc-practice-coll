package com.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TbPaymentTxnV2 {
    /**
     * 主键ID字段
     */
    private Long id;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 服务接口版本号
     */
    private String version;

    /**
     * 出款交易主单号
     */
    private String tradeMasterNo;

    /**
     * 请求批次号
     */
    private String merchantBatchNo;

    /**
     * 付款业务唯一id
     */
    private String orderId;

    /**
     * 付款订单号
     */
    private String tradeNo;

    /**
     * 订单状态码,(0：初始态，1：出款成功，2：出款失败，3：出款申请中， 4：出款处理中)
     */
    private Byte status;

    /**
     * 对外返回code
     */
    private String responseCode;

    /**
     * 对外返回msg
     */
    private String responseMsg;

    /**
     * 支付订单号
     */
    private String payOrderNo;

    /**
     * 支付返回code
     */
    private String payRspCode;

    /**
     * 支付返回msg
     */
    private String payRspMsg;

    /**
     * 出款金额
     */
    private Long amount;

    /**
     * 出款手续费
     */
    private Long fee;

    /**
     * 出款税费
     */
    private Long tax;

    /**
     * 货币代码，大写字母
     */
    private String currency;

    /**
     * 国家编号
     */
    private String countryCode;

    /**
     * 付款类型
     */
    private String payType;

    /**
     * 支付产品码
     */
    private String productCode;

    /**
     * 接入商户的用户唯一ID
     */
    private String custId;

    /**
     * Shareit支付会员ID
     */
    private String memberId;

    /**
     * 扩展信息
     */
    private String extraInfo;

    /**
     * 收款账号
     */
    private String payeeUpi;

    /**
     * 收款方姓名
     */
    private String payeeName;

    /**
     * 付款附言
     */
    private String transactionNote;

    /**
     * 交易结果后台回调地址
     */
    private String callbackUrl;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    /**
     * 订单数据创建时间
     */
    private Date createTime;

    private String creator;

    private Date modifiedTime;

    private String modifier;

    /**
     * 是否逻辑删除(0：未删除，1：已删除)
     */
    private Byte isDeleted;

    /**
     * 账号信息(JSON)
     */
    private String accountInfo;
}