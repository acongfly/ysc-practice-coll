package com.acongfly.redis.protoStuff;

import com.acongfly.redis.entity.OrderVO;
import com.acongfly.redis.util.ProtoStuffSerializerUtil;

/**
 * @program: study
 * @description: 序列化操作
 * @author: shicong yang
 * @create: 2019-04-28 17:59
 **/
public class ProtoStuffService {

    public void ProtoSerialization(OrderVO orderVO) {
        // 使用 protoStuff
        byte[] serialize = ProtoStuffSerializerUtil.serialize(orderVO);
        System.out.println(serialize.length);
    }

    // public void jdkSerial(OrderVO orderVO){
    // orderVO.
    // }

    public static void main(String[] args) {
        OrderVO build = OrderVO.builder().accountId("123").amt(1).orderId("234").build();
        byte[] serialize = ProtoStuffSerializerUtil.serialize(build);
        System.out.println(serialize.length);
    }
}
