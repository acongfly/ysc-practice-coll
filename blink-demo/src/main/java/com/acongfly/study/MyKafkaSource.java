// package com.acongfly.study;
//
// import java.util.Properties;
//
// import org.apache.flink.api.java.utils.ParameterTool;
// import org.apache.flink.streaming.api.datastream.DataStream;
// import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
// import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
// import org.apache.flink.table.api.DataTypes;
// import org.apache.flink.table.api.TableSchema;
// import org.apache.flink.table.sources.StreamTableSource;
// import org.apache.flink.table.types.DataType;
//
// import com.acongfly.study.common.KafkaConfigUtil;
// import com.acongfly.study.common.LogInfo;
// import com.acongfly.study.common.LogInfoSchema;
//
/// **
// * @program: ysc-practice-coll
// * @description: kafka blink source
// * @author: shicong yang
// * @create: 2020-02-13 17:01
// **/
// public class MyKafkaSource implements StreamTableSource<LogInfo> {
//
// ParameterTool parameterTool;
//
// public MyKafkaSource(ParameterTool parameterTool) {
// this.parameterTool = parameterTool;
// }
//
// @Override
// public DataStream<LogInfo> getDataStream(StreamExecutionEnvironment execEnv) {
// Properties properties = KafkaConfigUtil.buildKafkaProps(parameterTool);
// return execEnv.addSource(new FlinkKafkaConsumer011<>(parameterTool.get("kafka.topic"), new LogInfoSchema(),
// properties));
// }
//
//// @Override
//// public DataType getReturnType() {
//// return new RowType(new DataType[]{StringType.INSTANCE,
// LongType.INSTANCE,LongType.INSTANCE,LongType.INSTANCE,StringType.INSTANCE,StringType.INSTANCE},
//// new String[]{"bizMethod","createTime","methodEndTime","methodStartTime","reqMsg","respMsg"});
//// }
//
// @Override
// public TableSchema getTableSchema() {
// return TableSchema.builder().field("bizMethod", DataTypes.STRING())
// .field("createTime", DataTypes.BIGINT())
// .field("methodEndTime",DataTypes.BIGINT())
// .field("methodStartTime",DataTypes.BIGINT())
// .field("reqMsg",DataTypes.STRING())
// .field("respMsg",DataTypes.STRING())
// .build();
// }
//
//
// @Override
// public DataType getProducedDataType() {
// return DataTypes.ROW();
//// return new RowType(new DataType[]{DataTypes.STRING(),
// DataTypes.BIGINT(),DataTypes.BIGINT(),DataTypes.BIGINT(),DataTypes.STRING(),DataTypes.STRING()},
//// new String[]{"bizMethod","createTime","methodEndTime","methodStartTime","reqMsg","respMsg"});
// }
// }
