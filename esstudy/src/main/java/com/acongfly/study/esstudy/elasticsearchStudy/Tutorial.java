package com.acongfly.study.esstudy.elasticsearchStudy;

import java.io.Serializable;

import lombok.Data;

/**
 * @author sanglp
 * @create 2018-07-04 9:03
 * @desc 实体类
 **/
@Data
public class Tutorial implements Serializable {
    private Long id;
    // 教程名称
    private String name;
}