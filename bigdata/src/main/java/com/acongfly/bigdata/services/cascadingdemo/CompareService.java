package com.acongfly.bigdata.services.cascadingdemo;

import java.util.Properties;

import org.springframework.stereotype.Service;

import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.Aggregator;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Every;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

/**
 * @program: ysc-practice-coll
 * @description: csv文件比较
 * @author: shicong yang
 * @create: 2019-06-15 11:02
 **/
@Service
@Deprecated
public class CompareService {

    public void compareForCsv() {
        String batchCsvBefPath = "/Users/shicongyang/others/batchFile.csv";
        String batchCsvAftPath = "/Users/shicongyang/others/batchFile2.csv";
        String outputPath = "/Users/shicongyang/others";
        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, CompareService.class);
        FlowConnector flowConnector = new HadoopFlowConnector(properties);
        Fields batchCsvBefs =
            new Fields("b_merchantId", "b_tradeMasterNo", "b_merchantBatchNo", "b_orderId", "b_tradeNo", "b_status",
                "b_amount", "b_currency", "b_countryCode", "b_payType", "b_payeeUpi", "b_payeeName");
        Tap beforTap = new Hfs(new TextDelimited(batchCsvBefs, true, ","), batchCsvBefPath, SinkMode.REPLACE);
        Fields batchCsvAfts =
            new Fields("a_merchantId", "a_tradeMasterNo", "a_merchantBatchNo", "a_orderId", "a_tradeNo", "a_status",
                "a_amount", "a_currency", "a_countryCode", "a_payType", "a_payeeUpi", "a_payeeName");
        Tap afterTap = new Hfs(new TextDelimited(batchCsvAfts, true, ","), batchCsvAftPath, SinkMode.REPLACE);
        Fields outs = new Fields("o_amount");
        Tap outputTap = new Hfs(new TextDelimited(outs, true, ","), outputPath, SinkMode.REPLACE);

        FlowDef flowDef = FlowDef.flowDef();
        Pipe beforCsv = new Pipe("beforCsv");
        flowDef.addSource(beforCsv, beforTap);

        Aggregator b_amount = new Sum(new Fields("b_amount"), Double.class);

        Pipe sum = new Every(new Pipe("sum"), b_amount);
        Tap resultTap = new Hfs(new TextDelimited(outs, true, ","), String.format("%s/%s", outputPath, "result.csv"),
            SinkMode.REPLACE);
        flowDef.addTailSink(sum, resultTap);

        flowConnector.connect(flowDef).complete();
    }

}
