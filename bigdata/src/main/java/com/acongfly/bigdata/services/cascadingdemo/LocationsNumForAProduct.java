package com.acongfly.bigdata.services.cascadingdemo;

import cascading.flow.FlowConnector;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.pipe.HashJoin;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.CountBy;
import cascading.pipe.assembly.Retain;
import cascading.pipe.assembly.Unique;
import cascading.pipe.joiner.LeftJoin;
import cascading.property.AppProps;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

import java.util.Properties;

public class LocationsNumForAProduct {

    public static FlowDef createWorkflow(Tap usersTap, Tap transactionsTap, Tap outputTap) {
        Pipe transactionsPipe = new Pipe("transactions_pipe");

        Pipe usersPipe = new Pipe("join");
        Fields t_user_id = new Fields("user-id");
        Fields u_user_id = new Fields("id");
        Pipe joinPipe = new HashJoin(transactionsPipe, t_user_id, usersPipe, u_user_id, new LeftJoin());

        Pipe retainPipe = new Pipe("retain", joinPipe);
        retainPipe = new Retain(retainPipe, new Fields("product-id", "location"));

        Pipe cdistPipe = new Pipe("cdistPipe", retainPipe);
        Fields selector = new Fields("product-id", "location");
        cdistPipe = new Unique(cdistPipe, selector);

        Fields cdist = new Fields("cdist");
        Fields product_id = new Fields("product-id");
        cdistPipe = new CountBy(cdistPipe, product_id, cdist);

        FlowDef flowDef = FlowDef.flowDef()
                .addSource(transactionsPipe, transactionsTap)
                .addSource(usersPipe, usersTap)
                .addTailSink(cdistPipe, outputTap);
        return flowDef;
    }

    public static void main(String[] args) {

        //User information (id, email, language, location)
        //Transaction information (transaction-id, product-id, user-id, purchase-amount, item-description)
        String usersPath = args[0];
        String transactionsPath = args[1];
        String outputPath = args[2];

        Properties properties = new Properties();
        AppProps.setApplicationJarClass(properties, LocationsNumForAProduct.class);
        FlowConnector flowConnector = new HadoopFlowConnector(properties);

        Fields users = new Fields("id", "email", "language", "location");
        Tap usersTap = new Hfs(new TextDelimited(users, false, "\t"), usersPath);

        Fields transactions = new Fields("transaction-id", "product-id", "user-id", "purchase-amount", "item-description");
        Tap transactionsTap = new Hfs(new TextDelimited(transactions, false, "\t"), transactionsPath);

        Tap outputTap = new Hfs(new TextDelimited(false, "\t"), outputPath);

        FlowDef flowDef = createWorkflow(usersTap, transactionsTap, outputTap);
        flowConnector.connect(flowDef).complete();
    }
}
