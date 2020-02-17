// package com.acongfly.study;
//
// import java.util.Properties;
//
// import org.apache.flink.api.common.serialization.SimpleStringSchema;
// import org.apache.flink.streaming.api.datastream.DataStreamSource;
// import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
// import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
//
/// **
// * @program: ysc-practice-coll
// * @description:
// * @author: shicong yang
// * @create: 2020-02-13 22:29
// **/
// public class LogProcess01 {
//
// public static void main(String[] args) throws Exception {
// StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
// //kafka topic list
//// List<String> topics = Arrays.asList(parameterTool.get("metrics.topic"), parameterTool.get("logs.topic"));
//// Properties properties = KafkaConfigUtil.buildKafkaProps(ExecutionEnvUtil.PARAMETER_TOOL);
//// FlinkKafkaConsumer011<LogInfo> consumer = new
// FlinkKafkaConsumer011<>(ExecutionEnvUtil.PARAMETER_TOOL.get("kafka.topic"), new LogInfoSchema(), properties);
// //kafka topic Pattern
// //FlinkKafkaConsumer011<MetricEvent> consumer = new
// FlinkKafkaConsumer011<>(java.utils.regex.Pattern.compile("test-topic-[0-9]"), new MetricSchema(), props);
//
//
//// consumer.setStartFromLatest();
//// consumer.setStartFromEarliest()
//// DataStreamSource<LogInfo> data = env.addSource(consumer);
////
//// data.print();
////
//// env.execute("flink kafka connector test");
// String topic = "t1";
// Properties prop = new Properties();
// prop.setProperty("bootstrap.servers","localhost:9092");
// prop.setProperty("group.id","con1");
//
// FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), prop);
//
//// myConsumer.setStartFromGroupOffsets();//默认消费策略
//
// DataStreamSource<String> text = env.addSource(myConsumer);
//
// text.print().setParallelism(1);
//
// env.execute("StreamingFromCollection");
// }
// }
