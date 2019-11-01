package com.acongfly.studyjava.algorithm;

/**
 * @author shicongyang
 * @ClassName: InsertionSort
 * @Description: 插入排序 平均时间复杂度：O(n^2) 最好情况：O(n) 最坏情况：O(n^2) 空间复杂度：O(1) 占用常数类型不占用额外内存 是稳定的 算法简单描述： 1.从第一个元素开始，默认是经过排序的；
 *               2.到下一个元素，在已经排序好的序列中进行从后往前扫描； 3.如果已经排序好的该元素大于新元素，就将该元素进行向后移动操作；
 *               4.重复第三步的操作，直到找到已经排序好的队列中的元素小于或者是等于新元素的位置； 5.然后就将该新元素插入到该位置； 6.重复2~5步骤
 * @date 2018年3月24日 下午11:37:11
 */
public class InsertionSort {

    public static int[] insertionSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int current = 0;
        for (int i = 0; i < array.length - 1; i++) {
            current = array[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < array[preIndex]) { // 此处current < array[preIndex],不使用<=，如此便为稳定的
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex + 1] = current;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] insertionSort = insertionSort(array);
        for (int i : insertionSort) {
            System.out.print(i + ",");
        }
    }

}
