package com.acongfly.studyjava.javaStudy.beancopy;

import java.util.ArrayList;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/**
 * program: study
 * <p>
 * description:
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2018-10-24 17:35
 * <p>
 **/

public class BeanCopyTest {

    public static void main(String[] args) {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();

        SplitOrderReqDTO splitOrderReqDTO = new SplitOrderReqDTO();
        SplitOrderReqDTO splitOrderReqDTO1 = new SplitOrderReqDTO();
        ArrayList<SplitSubOrderInfoReqDTO> splitSubOrderInfoReqDTOS = new ArrayList<>();
        ArrayList<PayChannelDetailInfo> payChannelDetailInfos = new ArrayList<>();
        SplitSubOrderInfoReqDTO splitSubOrderInfoReqDTO = new SplitSubOrderInfoReqDTO();
        PayChannelDetailInfo payChannelDetailInfo = new PayChannelDetailInfo();
        splitOrderReqDTO.setTransactionId("123456");
        payChannelDetailInfo.setPayChannel(1);
        payChannelDetailInfos.add(payChannelDetailInfo);
        splitSubOrderInfoReqDTO.setPayChannelDetailInfoList(payChannelDetailInfos);
        splitSubOrderInfoReqDTOS.add(splitSubOrderInfoReqDTO);
        splitOrderReqDTO.setSubOrderInfoList(splitSubOrderInfoReqDTOS);

        // BeanCopyUtils.copyProperties(splitOrderReqDTO, splitOrderReqDTO1);
        mapper.map(splitOrderReqDTO, splitOrderReqDTO1);
        System.out.println(splitOrderReqDTO1.toString());
    }

}
