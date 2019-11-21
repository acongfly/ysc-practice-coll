package com.acongfly.springdatajpademo.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @program: ysc-practice-coll
 * @description: user
 * @author: shicong yang
 * @create: 2019-11-19 11:34
 **/
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Date cTime;
    private Integer roleId;

}
