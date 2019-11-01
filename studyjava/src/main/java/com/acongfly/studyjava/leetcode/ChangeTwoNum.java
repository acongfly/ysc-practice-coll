package com.acongfly.studyjava.leetcode;

/**
 * program: study
 * <p>
 * description: 实现两个int类型变量值的交换，要求不使用中间变量
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-02-14 15:31
 * <p>
 **/

public class ChangeTwoNum {

    public void changeTwoNum() {
        int num1 = 4;
        int num2 = 6;
        // 方法一
        // num1 = num1 + num2;
        // num2 = num1- num2;
        // num1 = num1 - num2;
        // System.out.println("num1="+num1+" "+"num2="+num2);

        // 方法二
        num1 = num1 ^ num2;
        num2 = num1 ^ num2;
        num1 = num1 ^ num2;
        System.out.println("num1=" + num1 + " " + "num2=" + num2);
    }

    public static void main(String[] args) {
        ChangeTwoNum changeTwoNum = new ChangeTwoNum();
        changeTwoNum.changeTwoNum();
    }
}
