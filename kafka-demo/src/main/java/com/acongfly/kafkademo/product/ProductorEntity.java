package com.acongfly.kafkademo.product;

import java.io.Serializable;

import lombok.Data;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-07-04 20:13
 **/
@Data
public class ProductorEntity implements Serializable {
    private static final long serialVersionUID = -1472179718365699370L;

    private String name;
    private int age;
    private String message;
}
