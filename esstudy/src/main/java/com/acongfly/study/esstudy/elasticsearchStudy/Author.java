package com.acongfly.study.esstudy.elasticsearchStudy;

import java.io.Serializable;

import lombok.Data;

/**
 * @author sanglp
 * @create 2018-07-04 9:04
 * @desc 作者实体类
 **/
@Data
public class Author implements Serializable {
    /**
     * 作者ID
     */
    private Long id;
    /**
     * 作者姓名
     */
    private String name;
    /**
     * 作者简介
     */
    private String remark;
    /**
     * 年龄
     */
    private int age;
}