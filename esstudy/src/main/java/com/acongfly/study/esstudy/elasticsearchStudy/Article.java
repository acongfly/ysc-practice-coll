package com.acongfly.study.esstudy.elasticsearchStudy;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * @author sanglp
 * @create 2018-07-04 9:06
 * @desc 文章实体类, 默认情况下添加@Document注解会对实体中的所有属性建立索引，
 * @Document注解里面的几个属性，类比mysql的话是这样： index –> DB type –> Table Document –>
 *                                  row @Id注解加上后，在Elasticsearch里相应于该列就是主键了，在查询时就可以直接用主键查询，后面再看。其实和mysql非常类似，基本就是一个数据库。
 **/
@Document(indexName = "projectname", type = "article", indexStoreType = "fs", shards = 5, replicas = 1,
    refreshInterval = "-1")
@Data
public class Article implements Serializable {

    @Id
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时间
     */
    private String postTime;
    /**
     * 点击率
     */
    private String clickCount;
    /**
     * 作者
     */
    private Author author;
    /**
     * 所属教程
     */
    private Tutorial tutorial;

    /**
     * 作者集合
     */
    private List<Author> authors;

    // @Field(format = DateFormat.date_hour_minute_second)
    // private Date createTime;
}