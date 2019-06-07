package com.acongfly.studyjava.algorithm;

import java.util.ArrayList;

/**
 * @author shicongyang
 * @ClassName: BucketSort
 * @Description: 桶排序
 * 平均时间复杂度：O(n+k)
 * 最好情况：O(n+k)
 * 最坏情况：O(n²)
 * 空间复杂度：O(n+k)
 * 占用额外的内存
 * 是稳定的排序方式：
 * 算法的简单描述：
 * 桶排序的工作原理：假使输入的数据服从均匀分布，将数据分到有限数量的桶里，每个桶再分别排序
 * （有可能是在使用别的排序算法或是递归的方式继续使用桶排序）
 * 1.人为的设置一个bucketSize,作为每个桶所能放置多少个不同的数值（例如当BucketSize==5的时候
 * ，该桶可以存放{1,2,3,4,5}这几种数字，但是容量不限制，即可以存放200个3）
 * 2.遍历输入数据，并且把数据一个一个放到对应的桶里去
 * 3.对每个不是空的桶进行排序，可以使用其他的排序方法，也可以递归使用桶排序
 * 4.从不是空的桶里把排好序的数据拼接起来
 * @date 2018年3月28日 下午11:13:18
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param array
     * @param bucketSize
     * @return
     */
    public static ArrayList<Integer> bucketSort(ArrayList<Integer> array, int bucketSize) {
        if (array == null || array.size() < 2)
            return array;
        int max = array.get(0), min = array.get(0);
        // 找到最大值最小值
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > max)
                max = array.get(i);
            if (array.get(i) < min)
                min = array.get(i);
        }
        int bucketCount = (max - min) / bucketSize + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketCount);
        ArrayList<Integer> resultArr = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < array.size(); i++) {
            bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
        }
        for (int i = 0; i < bucketCount; i++) {
            if (bucketSize == 1) { // 如果带排序数组中有重复数字时 
                for (int j = 0; j < bucketArr.get(i).size(); j++)
                    resultArr.add(bucketArr.get(i).get(j));
            } else {
                if (bucketCount == 1)
                    bucketSize--;
                ArrayList<Integer> temp = bucketSort(bucketArr.get(i), bucketSize);
                for (int j = 0; j < temp.size(); j++)
                    resultArr.add(temp.get(j));
            }
        }
        return resultArr;
    }


}
