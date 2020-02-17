package com.acongfly.study;

import java.util.Properties;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import com.acongfly.study.common.ExecutionEnvUtil;
import com.acongfly.study.common.KafkaConfigUtil;
import com.acongfly.study.common.LogInfo;
import com.acongfly.study.common.LogInfoSchema;
import org.apache.flink.types.Row;

/**
 * @program: ysc-practice-coll
 * @description: 日志处理
 * @author: shicong yang
 * @create: 2020-02-13 12:17
 **/
public class LogProcess {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // checkpoint配置
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig()
            .enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // EventTime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 使用blink
        EnvironmentSettings environmentSettings =
            EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment blinkStreamTableEnv = StreamTableEnvironment.create(env, environmentSettings);
        // 获取kafka配置
        ParameterTool parameterTool = ExecutionEnvUtil.PARAMETER_TOOL;
        Properties properties = KafkaConfigUtil.buildKafkaProps(parameterTool);

        DataStream<LogInfo> dataStream =
            env.addSource(new FlinkKafkaConsumer(parameterTool.get("kafka.topic"), new LogInfoSchema(), properties));
        dataStream.print();

        // table 1
        // Table table = blinkStreamTableEnv.fromDataStream(dataStream,
        // "bizMethod,createTime,methodEndTime,methodStartTime,reqMsg,respMsg");
        // blinkStreamTableEnv.registerTable("kafkaDataStream",table);
        // table 2
        blinkStreamTableEnv.registerDataStream("kafkaDataStream", dataStream,
            "bizMethod,createTime,methodEndTime,methodStartTime,reqMsg,respMsg");

        // blinkStreamTableEnv.registerDataStream("kafkaDataStream",dataStream);
        // RetractStreamTableSink<Row> retractStreamTableSink = new MyRetractStreamTableSink(new String[]{"_count",
        // "word"}, new DataType[]{DataTypes.BIGINT(), DataTypes.STRING()});
        // blinkStreamTableEnv.registerTableSink("sinkTable", retractStreamTableSink);

        Table table1 = blinkStreamTableEnv.sqlQuery("select count(*) from kafkaDataStream Group by bizMethod");
        blinkStreamTableEnv.toRetractStream(table1, Row.class).print();
        // System.out.println(table1.getSchema());
        blinkStreamTableEnv.execute("Blink Kafka Table Source");

    }
}
