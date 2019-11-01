package com.acongfly.bigdata.services.reconciliation;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryCollector;

/**
 * @program: ysc-practice-coll
 * @description: 汇总单 数据从Tap进入流程，通过几个Pipes，最后流入Sink。
 * @author: shicong yang
 * @create: 2019-06-14 11:02
 **/
public class OperateBill extends BaseOperation implements Function {

    public OperateBill(Fields fields) {
        super(fields);
    }

    @Override
    public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
        TupleEntry arguments = functionCall.getArguments();
        TupleEntryCollector collector = functionCall.getOutputCollector();

        Iterator<Comparable> iterator = arguments.getFields().iterator();
        Tuple tuple = new Tuple();
        while (iterator.hasNext()) {
            Comparable fieldName = iterator.next();
            String value = arguments.getString(fieldName);
            if (value == null) {
                value = "";
            }
            // else if (value.contains("\"")) {
            // value = value.replaceAll("\"", "");
            // }
            if (StringUtils.isBlank(value)) {
                tuple.add("-");
            } else {
                tuple.add(value);
            }
        }
        collector.add(tuple);
    }

}
