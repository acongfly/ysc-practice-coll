package com.acongfly.studyjava.algorithm;

import java.util.Arrays;

/**
 * @author shicongyang
 * @ClassName: CountingSort
 * @Description: 计数排序
 * 平均时间复杂度O(n+k)
 * 最好情况：O(n+k)
 * 最坏情况：O(n+k)
 * 空间复杂度：O(k)
 * 此算法占用额外的内存
 * 是稳定的排序算法
 * 算法的基本描述：
 * 1.找出待排序的数组中的最大和最小的元素
 * 2.统计出数组中的每个值的元素出现的次数，存入数组c的第i项；
 * 3.对所有的计数累加（从c中的第一个元素开始，每一项和前一项相加）；
 * 4.反向填充目标数组：将每个元素i放到新数组的第Ci项，每放一次元素就将Ci减去1
 * @date 2018年3月28日 下午10:43:51
 */
public class CountingSort {

    /**
     * 计数排序
     *
     * @param array
     * @return
     */
    public static int[] countingSort(int[] array) {
        if (array.length == 0) return array;
        int bias, min = array[0], max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
        }
        bias = 0 - min;
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);
        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }
        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else
                i++;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] countingSort = countingSort(array);
        for (int i : countingSort) {
            System.out.print(i + ",");
        }
    }


}
