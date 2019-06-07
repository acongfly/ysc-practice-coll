package com.acongfly.study.esstudy.elasticsearchStudy;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sanglp
 * @create 2018-07-04 9:03
 * @desc 实体类
 **/
@Data
public class Tutorial implements Serializable {
    private Long id;
    //教程名称
    private String name;
}