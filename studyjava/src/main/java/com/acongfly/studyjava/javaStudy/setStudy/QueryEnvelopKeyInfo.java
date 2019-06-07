package com.acongfly.studyjava.javaStudy.setStudy;

import lombok.Data;

import java.util.Objects;

/**
 * program: envelope-parent<p>
 * description: 查询红包计算表中的相关key的信息<p>
 * author: shicong yang<p>
 * createDate: 2018-11-20 17:28<p>
 **/
@Data
public class QueryEnvelopKeyInfo implements Comparable<QueryEnvelopKeyInfo> {

    /**
     * 库的名称
     */
    private String dataBaseName = "carlife_envelope";

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 主键ID
     */
    private long id;


    @Override
    public int compareTo(QueryEnvelopKeyInfo o) {
        return (Objects.hash(String.format("%s%S%S", o.getDataBaseName(), o.getTableName(), o.getId())) - Objects.hash(String.format("%s%S%S", this.getDataBaseName(), this.getTableName(), this.getId())));
    }
}
