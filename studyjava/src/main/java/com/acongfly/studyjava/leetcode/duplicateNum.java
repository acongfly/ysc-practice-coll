package com.acongfly.studyjava.leetcode;

import java.util.Arrays;

/**
 * @program: ysc-practice-coll
 * @description: 在一个长度为n的数组里，数组都在0————n-1中，数组中有一些重复的元素，但是不知道重复几次，不知道那个重复了，找出数组中重复的元素 要求复杂度O(N)+O(1),即时间复杂度O(N),空间复杂度O(1)
 * @author: shicong yang
 * @create: 2019-08-29 20:25
 **/
public class duplicateNum {

    /**
     * 解题思路是：可以按照元素的值放在数组指定的位置上，如果指定的位置和元素值不匹配，则表示重复
     */
    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5};
        int[] dulication = new int[1];
        duplicateNum duplicateNum = new duplicateNum();
        boolean duplicate = duplicateNum.duplicate(nums, 6, dulication);
        System.out.println(duplicate);
        System.out.println(Arrays.toString(dulication));

    }

    /**
     * @param nums
     *            元素数组
     * @param length
     *            数组长度
     * @param duplication
     *            重复的元素
     * @return
     */
    public boolean duplicate(int[] nums, int length, int[] duplication) {
        if (nums == null || length <= 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    duplication[0] = nums[i];
                    return true;
                }
                // 交换
                int t = nums[i];
                nums[i] = nums[t];
                nums[t] = t;
            }
        }
        return false;
    }

}
