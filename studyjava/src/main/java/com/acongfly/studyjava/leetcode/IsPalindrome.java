package com.acongfly.studyjava.leetcode;/**
 * Created by hc on 2019/2/13.
 */

/**
 * program: study<p>
 * description: 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。<p>
 * author: shicong yang<p>
 * createDate: 2019-02-13 22:58<p>
 **/

public class IsPalindrome {

    public boolean isPalindrome(int x) {
        int y = x;
        StringBuilder sb = new StringBuilder();
        sb.append(y);
        String s = sb.reverse().toString();
        System.out.println(s);
        try {
            if (Integer.valueOf(s).equals(x)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        IsPalindrome isPalindrome = new IsPalindrome();
        boolean result = isPalindrome.isPalindrome(121);
        System.out.println(result);
    }
}
