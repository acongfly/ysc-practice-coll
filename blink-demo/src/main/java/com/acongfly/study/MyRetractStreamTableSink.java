package com.acongfly.study;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.sinks.RetractStreamTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.utils.TypeConversions;
import org.apache.flink.types.Row;

/**
 * @program: ysc-practice-coll
 * @description: 自定义sink
 * @author: shicong yang
 * @create: 2020-02-25 19:49
 **/
public class MyRetractStreamTableSink implements RetractStreamTableSink<Row> {

    private TableSchema tableSchema;

    // public MyRetractStreamTableSink(String[] fieldNames, TypeInformation[] typeInformations) {
    // this.tableSchema = new TableSchema(fieldNames, typeInformations);
    // }

    public MyRetractStreamTableSink(String[] fieldNames, DataType[] dataTypes) {
        this.tableSchema = TableSchema.builder().fields(fieldNames, dataTypes).build();
    }

    @Override
    public TypeInformation<Row> getRecordType() {
        return new RowTypeInfo(TypeConversions.fromDataTypeToLegacyInfo(tableSchema.getFieldDataTypes()),
            tableSchema.getFieldNames());
    }

    @Override
    public TableSchema getTableSchema() {
        return tableSchema;
    }

    @Override
    public TableSink<Tuple2<Boolean, Row>> configure(String[] strings, TypeInformation<?>[] typeInformations) {
        return null;
    }

    @Override
    public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {

    }

    @Override
    public DataStreamSink<?> consumeDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        // 官方建议实现consumeDataStream，emitDataStream后面会进行删除操作，不建议实现

        return dataStream.addSink(new SinkFunction<Tuple2<Boolean, Row>>() {
            @Override
            public void invoke(Tuple2<Boolean, Row> value, Context context) throws Exception {
                // 自定义Sink
                // f0==true :插入新数据
                // f0==false:删除旧数据
                if (value.f0) {
                    // 可以写入MySQL、Kafka或者发HttpPost...根据具体情况开发
                    System.out.println("insert=" + value.f1);
                }
            }
        });
    }
}
