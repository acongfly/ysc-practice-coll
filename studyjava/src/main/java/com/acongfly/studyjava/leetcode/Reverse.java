package com.acongfly.studyjava.leetcode;/**
                                         * Created by hc on 2019/2/13.
                                         */

/**
 * program: study
 * <p>
 * description: 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2019-02-13 22:19
 * <p>
 **/

public class Reverse {
    public int reverse(int x) {
        String s = String.valueOf(x);
        StringBuilder sb = new StringBuilder();
        try {
            if (s.startsWith("-")) {
                // 负数
                String value = s.substring(1, s.length());
                sb.append(value);
                String s1 = sb.reverse().toString();
                return Integer.valueOf("-" + s1);
            } else {
                return Integer.valueOf(sb.append(x).reverse().toString());
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        Reverse reverse = new Reverse();
        System.out.println(Integer.MAX_VALUE);
        System.out.println(reverse.reverse(Integer.MAX_VALUE));
    }
}
