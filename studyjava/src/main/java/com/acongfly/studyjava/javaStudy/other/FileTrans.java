package com.acongfly.studyjava.javaStudy.other;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @program: study
 * @description: 文件传输测试
 * @author: shicong yang
 * @create: 2019-04-19 10:10
 **/
@Data
public class FileTrans {

    // private FileInputStream fileInputStream;

    @NotBlank
    private String merId;

    @NotNull
    private byte[] content;
}
