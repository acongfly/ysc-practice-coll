package com.acongfly.study.common;

import java.io.IOException;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

/**
 * @program: ysc-practice-coll
 * @description: logInfo Schema ，支持序列化和反序列化
 * @author: shicong yang
 * @create: 2020-02-13 22:10
 **/
public class LogInfoSchema implements DeserializationSchema<LogInfo>, SerializationSchema<LogInfo> {
    @Override
    public LogInfo deserialize(byte[] bytes) throws IOException {
        return JSON.parseObject(new String(bytes), LogInfo.class);
    }

    @Override
    public boolean isEndOfStream(LogInfo logInfo) {
        return false;
    }

    @Override
    public byte[] serialize(LogInfo logInfo) {
        return JSON.toJSONString(logInfo).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public TypeInformation<LogInfo> getProducedType() {
        return TypeInformation.of(LogInfo.class);
    }
}
