package com.acongfly.studyjava.leetcode;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * program: study
 * <p>
 * description: 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 示例: 给定 nums = [2, 7, 11, 15], target = 9 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1] author: shicong yang
 * <p>
 * createDate: 2019-02-12 14:51
 * <p>
 **/

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        if (nums.length < 1) {
            return null;
        }
        Map<Integer, Integer> map = Maps.newHashMap();
        for (int i = 0; i < nums.length; i++) {
            int i1 = target - nums[i];
            if (map.containsKey(i1)) {
                return new int[] {map.get(i1), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
