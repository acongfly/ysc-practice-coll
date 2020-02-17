package com.acongfly.study;

import java.util.Properties;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import com.acongfly.study.common.ExecutionEnvUtil;
import com.acongfly.study.common.KafkaConfigUtil;
import com.acongfly.study.common.LogInfoSchema;
import org.apache.flink.table.sinks.RetractStreamTableSink;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;

/**
 * @program: ysc-practice-coll
 * @description: 订单支付对账（source from kafka,比较金额和状态,sink to kafka）
 *
 *               https://blog.csdn.net/baifanwudi/article/details/87883278
 * @author: shicong yang
 * @create: 2020-02-13 12:14
 **/
public class OrderReconDemo {
    public static void main(String[] args) throws Exception {
        // set up execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
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

        // use blink planner in streaming mode 使用blink
        EnvironmentSettings blink = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment stEnv = StreamTableEnvironment.create(env, blink);

        // 获取kafka配置
        ParameterTool parameterTool = ExecutionEnvUtil.PARAMETER_TOOL;
        Properties properties = KafkaConfigUtil.buildKafkaProps(parameterTool);

        // 获取 kafka source
        FlinkKafkaConsumer flinkKafkaConsumer =
            new FlinkKafkaConsumer(parameterTool.get("kafka.topic"), new LogInfoSchema(), properties);

        // add source
        Table source = stEnv.fromDataStream(env.addSource(flinkKafkaConsumer),
            "bizMethod,createTime,methodEndTime,methodStartTime,reqMsg,respMsg");

        Table result = stEnv.sqlQuery("select count(*) as logSum ,bizMethod from " + source + " group by bizMethod");

        // 使用 toRetractStream 不使用 toAppendStream
        stEnv.toRetractStream(result, Row.class).print();

        // sink
        // RetractStreamTableSink<Row> retractStreamTableSink = new MyRetractStreamTableSink(new String[]{"_count",
        // "word"}, new DataType[]{DataTypes.BIGINT(), DataTypes.STRING()});
        // stEnv.registerTableSink("sinkTable", retractStreamTableSink);

        stEnv.execute("loginfo kafka learn");
    }

    /**
     * Simple POJO.
     */
    public static class Order {
        public Long user;
        public String product;
        public int amount;

        public Order() {}

        public Order(Long user, String product, int amount) {
            this.user = user;
            this.product = product;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Order{" + "user=" + user + ", product='" + product + '\'' + ", amount=" + amount + '}';
        }
    }

    /**
     * Simple POJO.
     */
    public static class OrderB {
        public Long user;
        public String product;
        public int amount;

        public OrderB() {}

        public OrderB(Long user, String product, int amount) {
            this.user = user;
            this.product = product;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "OrderB{" + "user=" + user + ", product='" + product + '\'' + ", amount=" + amount + '}';
        }
    }

    public static class Result {
        public int logSum;
        public String bizMethod;

        public Result() {}

        public Result(int logSum, String bizMethod) {
            this.bizMethod = bizMethod;
            this.logSum = logSum;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Result{");
            sb.append("logSum=").append(logSum);
            sb.append(", bizMethod='").append(bizMethod).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
