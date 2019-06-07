package com.acongfly.studyjava.javaStudy.comparStudy;

/**
 * @author shicongyang
 * @Description: comparable学习：Comparable可以认为是一个内比较器，实现了Comparable接口的类有一个特点，就是这些类是可以和自己比较的
 * @date 2018年4月25日 下午2:28:16
 */
public class comparableStudy implements Comparable<comparableStudy> {

    private String str;

    public comparableStudy(String str) {
        this.str = str;
    }
//	compareTo方法的返回值是int，有三种情况:
//	1、比较者大于被比较者（也就是compareTo方法里面的对象），那么返回正整数
//	2、比较者等于被比较者，那么返回0
//	3、比较者小于被比较者，那么返回负整数

    @Override
    public int compareTo(comparableStudy o) {
        if (this.str.compareTo(o.str) > 0) {
            return 1;
        } else if (this.str.compareTo(o.str) == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public static void main(String[] args) {
        comparableStudy a = new comparableStudy("a");
        comparableStudy b = new comparableStudy("b");
        comparableStudy c = new comparableStudy("c");
        comparableStudy d = new comparableStudy("d");
        System.out.println(a.compareTo(a));
        System.out.println(b.compareTo(b));
        System.out.println(c.compareTo(d));
        System.out.println(d.compareTo(c));
    }
}

