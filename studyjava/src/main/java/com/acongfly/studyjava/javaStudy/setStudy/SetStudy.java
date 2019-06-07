package com.acongfly.studyjava.javaStudy.setStudy;

import java.util.TreeSet;

/**
 * program: study<p>
 * description: set学习<p>
 * author: shicong yang<p>
 * createDate: 2018-12-03 10:05<p>
 **/

public class SetStudy {

    public static void main(String[] args) {
        TreeSet<QueryEnvelopKeyInfo> queryEnvelopKeyInfos = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            QueryEnvelopKeyInfo queryEnvelopKeyInfo = new QueryEnvelopKeyInfo();
            queryEnvelopKeyInfo.setDataBaseName("enve");
            queryEnvelopKeyInfo.setId(i);
            queryEnvelopKeyInfo.setTableName("enve_comp_" + i);
            queryEnvelopKeyInfos.add(queryEnvelopKeyInfo);
        }

        System.out.println(queryEnvelopKeyInfos);
    }
}
