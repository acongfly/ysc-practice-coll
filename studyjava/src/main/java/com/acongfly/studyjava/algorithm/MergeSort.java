package com.acongfly.studyjava.algorithm;

import java.util.Arrays;

/**
 * @author shicongyang
 * @ClassName: MergeSort
 * @Description: 归并排序
 * 平均的时间复杂度O(nlogn)
 * 最好情况：O(nlogn)
 * 最坏情况：O(nlogn)
 * 空间复杂度：O(n)
 * 排序方式：占用额外内存
 * 是稳定排序
 * 算法简单描述：
 * 1.把长度为n的输入序列分成两个长度为n/2的子序列
 * 2.对这两个子序列分别采用归并排序
 * 3.将两个排序好的序列合并成一个最终的有序序列
 * @date 2018年3月28日 下午9:30:13
 */
public class MergeSort {

    /**
     * @param @param  array
     * @param @return
     * @return int[]
     * @throws
     * @Title: MergeSort
     * @Description: 归并排序
     * @author shicongyang
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }

    /**
     * @param @param  left
     * @param @param  right
     * @param @return
     * @return int[]
     * @throws
     * @Title: merge
     * @Description: 归并排序中--将两段排序好的数组结合成一个排序数组
     * @author shicongyang
     */
    public static int[] merge(int[] left, int[] right) {

        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }


    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] mergeSort = MergeSort(array);
        for (int i : mergeSort) {
            System.out.print(i + ",");
        }
    }


}
