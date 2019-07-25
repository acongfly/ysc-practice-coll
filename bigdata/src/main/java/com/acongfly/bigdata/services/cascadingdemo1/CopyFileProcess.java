package com.acongfly.bigdata.services.cascadingdemo1;

import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;

import java.util.Properties;

/**
 * @program: ysc-practice-coll
 * @description: 文件copy处理(http : / / docs.cascading.org / impatient / impatient1.html)
 * @author: shicong yang
 * @create: 2019-06-15 20:10
 **/
public class CopyFileProcess {

    /**
     * description: copy file use cascading core<p>
     * param: [] <p>
     * return: void <p>
     * author: shicong yang <p>
     * date: 2019-06-15 <p>
     */
    public static void copyFileByCascading() {
        //入文件路径
        String inFilePath = "/Users/shicongyang/others/batchFile.csv";
        //出文件路径
        String outFilePath = "/Users/shicongyang/work/bak";

        /**类比水龙头出水案例*/
        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, CopyFileProcess.class);
        AppProps.setApplicationName(properties, "cascading demo 1 file copy");
        AppProps.addApplicationTag(properties, "technology:Cascading");

        FlowConnector flowConnector = new HadoopFlowConnector(properties);
//        Hadoop2MR1FlowConnector flowConnector = new Hadoop2MR1FlowConnector(properties);

        //我们创建一个源点击以指定输入数据。该数据恰好被格式化为带有标题行的制表符分隔值
        Tap inTap = new Hfs(new TextDelimited(true, ","), inFilePath, SinkMode.REPLACE);

        //创建一个水槽的水龙头
        //SinkMode.REPLACE 表示这个路径下如果有其他文件会被覆盖
        Tap outTap = new Hfs(new TextDelimited(true, ","), outFilePath, SinkMode.REPLACE);

        //指定连接水龙头的管道
        Pipe copyPipe = new Pipe("copy");

        //将水龙头，管道等连接成流动
        FlowDef copy = FlowDef.flowDef().addSource(copyPipe, inTap).addTailSink(copyPipe, outTap).setName("copy");
        //运行流程
        flowConnector.connect(copy).complete();
    }

    public static void main(String[] args) {

        copyFileByCascading();

    }


}
