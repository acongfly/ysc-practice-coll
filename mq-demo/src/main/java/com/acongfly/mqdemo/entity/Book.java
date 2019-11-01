package com.acongfly.mqdemo.entity;

import lombok.Data;

/**
 * description: book
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-05-21
 * <p>
 */
@Data
public class Book implements java.io.Serializable {

    private static final long serialVersionUID = -2164058270260403154L;

    private String id;
    private String name;

}
