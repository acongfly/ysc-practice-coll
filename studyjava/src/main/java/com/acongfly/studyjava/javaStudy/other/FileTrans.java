package com.acongfly.studyjava.javaStudy.other;

import lombok.Data;

import java.io.FileInputStream;
import java.io.Serializable;

/**
 * @program: study
 * @description: 文件传输测试
 * @author: shicong yang
 * @create: 2019-04-19 10:10
 **/
@Data
public class FileTrans implements Serializable {

//    private FileInputStream fileInputStream;

    private String merId;

    private byte[] content;
}
