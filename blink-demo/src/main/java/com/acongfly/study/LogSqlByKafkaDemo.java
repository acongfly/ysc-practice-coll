package com.acongfly.study;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * @program: ysc-practice-coll
 * @description: http://wuchong.me/blog/2019/09/02/flink-sql-1-9-read-from-kafka-write-into-mysql/(此版本不支持)
 * @author: shicong yang
 * @create: 2020-02-16 14:16
 **/
// TODO
public class LogSqlByKafkaDemo {
    public static void main(String[] args) throws Exception {
        // set up execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // use blink planner in streaming mode,
        // because watermark statement is only available in blink planner.
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        // start a checkpoint every 1000 ms
        env.enableCheckpointing(1000);
        // advanced options:

        // set mode to exactly-once (this is the default)
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // make sure 500 ms of progress happen between checkpoints
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        // checkpoints have to complete within one minute, or are discarded
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        // allow only one checkpoint to be in progress at the same time
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // enable externalized checkpoints which are retained after job cancellation
        env.getCheckpointConfig()
            .enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // allow job recovery fallback to checkpoint when there is a more recent savepoint
        env.getCheckpointConfig().setPreferCheckpointForRecovery(true);

        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, settings);

        String ddl = "CREATE TABLE log (\n" + "    bizMethod VARCHAR,\n" + "    createTime BIGINT,\n"
            + "    methodEndTime BIGINT,\n" + "    methodStartTime BIGINT,\n" + "    reqMsg VARCHAR,\n"
            + "    respMsg VARCHAR,\n" + "    ts as to_timestamp(cast(`createTime` as VARCHAR)),\n"
            + "    WATERMARK FOR ts AS ts - INTERVAL '3' SECOND\n" + ") WITH (\n" + "    'connector.type' = 'kafka',\n"
            + "    'connector.version' = 'universal',  \n" + "    'connector.topic' = 'blinklog',  \n"
            + "    'connector.startup-mode' = 'earliest-offset', \n"
            + "    'connector.properties.0.key' = 'zookeeper.connect',  \n"
            + "    'connector.properties.0.value' = 'localhost:2181', \n"
            + "    'connector.properties.1.key' = 'bootstrap.servers',\n"
            + "    'connector.properties.1.value' = 'localhost:9092', \n" + "    'update-mode' = 'append',\n"
            + "    'format.type' = 'json',  \n" + "    'format.derive-schema' = 'true' \n" + ")";

        tEnv.sqlUpdate(ddl);

        // run a SQL query on the table and retrieve the result as a new Table
        String query = "SELECT\n" +
        // " CAST(TUMBLE_START(ts, INTERVAL '5' SECOND) AS STRING) window_start,\n" +
            "  COUNT(*)  \n" +
            // " SUM(amount) total_amount,\n" +
            // " COUNT(DISTINCT product) unique_products\n" +
            "FROM log\n" + "GROUP BY bizMethod";
        Table result = tEnv.sqlQuery(query);
        tEnv.toRetractStream(result, Row.class).print();

        // submit the job
        tEnv.execute("Streaming Window SQL Job");
    }
}
