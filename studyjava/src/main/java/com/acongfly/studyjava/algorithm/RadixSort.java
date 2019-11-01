package com.acongfly.studyjava.algorithm;

import java.util.ArrayList;

/**
 * @author shicongyang
 * @ClassName: RadixSort
 * @Description: 基数排序 平均时间复杂度是：O(K*n) 最好情况：O(k*n) 最坏情况：O(k*n) 空间复杂度：O(n+k) 占用额外的内存 此算法是稳定的 算法的基本描述： 1.取得数组中的最大数，并取得位数
 *               2.arr为原始的数组，从最低位开始取每个位组成radix数组； 3.对radix进行计数排序，（利用计数排序适用于小范围数的特点）
 * @date 2018年3月28日 下午11:33:21
 */
public class RadixSort {

    /**
     * 基数排序
     *
     * @param array
     * @return
     */
    public static int[] radixSort(int[] array) {
        if (array == null || array.length < 2)
            return array;
        // 1.先算出最大数的位数；
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }
        int mod = 10, div = 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 10; i++)
            bucketList.add(new ArrayList<Integer>());
        for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
            for (int j = 0; j < array.length; j++) {
                int num = (array[j] % mod) / div;
                bucketList.get(num).add(array[j]);
            }
            int index = 0;
            for (int j = 0; j < bucketList.size(); j++) {
                for (int k = 0; k < bucketList.get(j).size(); k++)
                    array[index++] = bucketList.get(j).get(k);
                bucketList.get(j).clear();
            }
        }
        return array;
    }

}
