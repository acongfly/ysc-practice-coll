package com.acongfly.bigdata.services.reconciliation;

import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.pipe.joiner.OuterJoin;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import cn.hutool.core.text.csv.CsvWriter;
import com.google.common.collect.Lists;
import com.mapper.Result;
import com.mapper.TbPaymentTxnV2Mapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @program: ysc-practice-coll
 * @description: 对账demo
 * @author: shicong yang
 * @create: 2019-06-13 21:30
 **/
@Slf4j
@Service
public class ReconciliationPorcessService {

    @Resource
    private TbPaymentTxnV2Mapper tbPaymentTxnV2Mapper;

    enum FILTER_TYPE {
        DB_NOT_EXIST,
        WX_NOT_EXIST,
        EQUAL_DATA
    }


    private static final String WX_NOT_PATH = "wx_not";
    private static final String DB_NOT_PATH = "db_not";
    private static final String EQUAL_PATH = "equal";

    private static final String SUCCESS_FILE = "_SUCCESS";

    private static final String[] DB_BILL_SOURCE = {
            "merchantId",
            "tradeMasterNo",
            "merchantBatchNo",
            "orderId",
            "tradeNo",
            "status",
            "amount",
            "currency",
            "countryCode",
            "payType",
            "payeeUpi",
            "payeeName"

    };
    private static final String[] DB_BILL_SOURCE_WX = {
            "wx_merchantId",
            "wx_tradeMasterNo",
            "wx_merchantBatchNo",
            "wx_orderId",
            "wx_tradeNo",
            "wx_status",
            "wx_amount",
            "wx_currency",
            "wx_countryCode",
            "wx_payType",
            "wx_payeeUpi",
            "wx_payeeName"

    };

    Fields baseField = new Fields(
            "merchantId",
            "tradeMasterNo",
            "merchantBatchNo",
            "orderId",
            "tradeNo",
            "status",
            "amount",
            "currency",
            "countryCode",
            "payType",
            "payeeUpi",
            "payeeName"
    );

    private static final String[] DB_SUM_SOURCE = {
            "sumAmt",
            "sumSize"
    };

    public void creatTxnCsv() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Result> byUpdateTime = tbPaymentTxnV2Mapper.findByUpdateTime(System.currentTimeMillis() + "");
        CsvWriter csvWriter = new CsvWriter(new File("/Users/shicongyang/others/batchFile.csv"));
        List<String[]> objects = Lists.newLinkedList();
        objects.add(DB_BILL_SOURCE);
        int size = byUpdateTime.size();
        String[] a;
        for (int i = 0; i < size; i++) {
            Result result = byUpdateTime.get(i);
            a = new String[]{result.getMerchantId(), result.getTradeMasterNo(), result.getMerchantBatchNo(), result.getOrderId(),
                    result.getTradeNo(), result.getStatus().toString(), result.getAmount().toString(), result.getCurrency(), result.getCountryCode(), result.getPayType(),
                    result.getPayeeUpi(), result.getPayeeName()};
            objects.add(a);
        }
        csvWriter.write(objects);
        csvWriter.flush();
        csvWriter.close();
        stopWatch.stop();
        log.info("耗时{}ms", stopWatch.getTotalTimeMillis());
    }


    public void sumProcess() throws IOException {
        File file = new File("/Users/shicongyang/others/batchFile2.csv");
        String sourceFile = file.getAbsolutePath();
        String dbBillPath = "/Users/shicongyang/others/batchFile.csv";
        String targetDir = "/Users/shicongyang/others";

        JobConf jobConf = new JobConf(new Configuration());
        jobConf.setJarByClass(ReconciliationPorcessService.class);
        FileSystem fileSystem = FileSystem.get(jobConf);

        Properties properties = AppProps.appProps()
                .setName(ReconciliationPorcessService.class.getSimpleName() + "App")
                .setVersion("0.1")
                .buildProperties(jobConf);

        AppProps.setApplicationJarClass(properties, ReconciliationPorcessService.class);
        FlowConnector flowConnector = new HadoopFlowConnector(properties);
        FlowDef flowDef = FlowDef.flowDef();

        Fields wxSourceFields;
        Fields dbSourceFields;
        Fields wxFields;
        Fields dbFields;
        {
            wxSourceFields = new Fields();
            wxFields = new Fields();
            for (String fn : DB_BILL_SOURCE_WX) {
                wxSourceFields = wxSourceFields.appendSelector(new Fields(StringUtils.trim(fn) + "_1"));
                wxFields = wxFields.appendSelector(new Fields(StringUtils.trim(fn)));
            }
            dbSourceFields = new Fields();
            dbFields = new Fields();
            for (String fn : DB_BILL_SOURCE) {
                dbSourceFields = dbSourceFields.appendSelector(new Fields(StringUtils.trim(fn) + "_1"));
                dbFields = dbFields.appendSelector(new Fields(StringUtils.trim(fn)));
            }
        }

        // 输入
        Pipe wxPipe = new Pipe("wxPipe");
        Pipe dbPipe = new Pipe("dbPipe");
        Tap wxTap = new Hfs(new TextDelimited(wxSourceFields, true, ","), sourceFile);
        Tap dbWxPayTap = new Hfs(new TextDelimited(dbSourceFields, true, ","), dbBillPath);
        flowDef.addSource(wxPipe, wxTap);
        flowDef.addSource(dbPipe, dbWxPayTap);

        // 开始处理逻辑
        // ## wx
        Each filterQuota = new Each(wxPipe, wxSourceFields, new OperateBill(wxFields), wxFields);
        Each filterDbPipe = new Each(dbPipe, dbSourceFields, new OperateBill(dbFields), dbFields);
        Fields payOrderJoinFields = new Fields().appendSelector(wxFields).appendSelector(dbFields);
        Pipe allJoin = new CoGroup(
                filterQuota, new Fields("wx_orderId", "wx_merchantBatchNo"),
                filterDbPipe, new Fields("orderId", "merchantBatchNo"),
                payOrderJoinFields, new OuterJoin());
        // 0.2.1 DB不存在的记录
        Pipe equalDataPipe = new Each(new Pipe("equalDataPipe", allJoin), new FilterByType(FILTER_TYPE.EQUAL_DATA));
        Pipe dbNotExistPipe = new Each(new Pipe("dbNotExistPipe", allJoin), new FilterByType(FILTER_TYPE.DB_NOT_EXIST));
        Pipe wxNotExistPipe = new Each(new Pipe("wxNotExistPipe", allJoin), new FilterByType(FILTER_TYPE.WX_NOT_EXIST));

        Tap wxNotTap = new Hfs(new TextDelimited(baseField, true, ","), String.format("%s/%s", targetDir, WX_NOT_PATH), SinkMode.REPLACE);
        Tap dbNotTap = new Hfs(new TextDelimited(baseField, true, ","), String.format("%s/%s", targetDir, DB_NOT_PATH), SinkMode.REPLACE);
        Tap equalTap = new Hfs(new TextDelimited(baseField, true, ","), String.format("%s/%s", targetDir, EQUAL_PATH), SinkMode.REPLACE);
        flowDef.addTailSink(equalDataPipe, equalTap);
        flowDef.addTailSink(dbNotExistPipe, dbNotTap);
        flowDef.addTailSink(wxNotExistPipe, wxNotTap);
        /**/
        flowConnector.connect(flowDef).complete();
        //
        for (String path : new String[]{WX_NOT_PATH, DB_NOT_PATH, EQUAL_PATH}) {
            if (!fileSystem.exists(new Path(String.format("%s/%s/%s", targetDir, path, SUCCESS_FILE)))) {
                log.error(ReconciliationPorcessService.class.getSimpleName() + " Exit With Stat " + 2);
                System.exit(2);
            }
        }
        fileSystem.create(new Path(String.format("%s/%s", targetDir, SUCCESS_FILE)));
    }


}
