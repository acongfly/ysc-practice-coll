package com.acongfly.bigdata.services.cascadingdemo2;


import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexSplitGenerator;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

import java.util.Properties;

/**
 * @program: ysc-practice-coll
 * @description: 字数统计
 * @author: shicong yang
 * @create: 2019-06-17 10:39
 **/
public class WordCount {

    public static void main(String[] args) {
        String docFilePath = "/Users/shicongyang/others/batchFile.csv";
        String wcFilePath = "/Users/shicongyang/work/bak";

        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, WordCount.class);
        AppProps.setApplicationName(properties, "word count");
        AppProps.addApplicationTag(properties, "word count tag");

        FlowConnector hadoopFlowConnector = new HadoopFlowConnector(properties);
        Tap docFileTap = new Hfs(new TextDelimited(true, ","), docFilePath);
        Tap wcFileTap = new Hfs(new TextDelimited(true, ","), wcFilePath, SinkMode.REPLACE);

        Fields token = new Fields("token");
        Fields doc = new Fields("payeeName");

        RegexSplitGenerator regexSplitGenerator = new RegexSplitGenerator(token, "[,\\[\\]\\(\\),.]");
        // only returns "token"
        Each docPipe = new Each("token", doc, regexSplitGenerator, Fields.ALL);

        // determine the word counts 确定字数
        Pipe wc = new Pipe("wc", docPipe);
        wc = new GroupBy(wc, token);
        wc = new Every(wc, Fields.ALL, new Count(), Fields.ALL);

        //connect
        FlowDef wordCount = FlowDef.flowDef().addSource(docPipe, docFileTap).addTailSink(wc, wcFileTap).setName("wordCount");
        Flow connect = hadoopFlowConnector.connect(wordCount);
        connect.writeDOT("dot/wordCount.dot");
        connect.complete();
    }
}
