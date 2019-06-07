package com.acongfly.studyjava.algorithm;

/**
 * @author shicongyang
 * @ClassName: SelectionSort
 * @Description: 选择排序
 * 平均时间复杂度：O(n²);
 * 最好情况：O(n²);
 * 最坏情况：O(n²);
 * 空间复杂度：O(1);
 * 占用常数内存，不占用额外内存
 * 不稳定
 * 算法简单的描述：n个元素的直接选择排序，可经过n-1趟直接排序得到有序的结果：
 * 1.初始的状态是，无序的区域为A[1,...,n],有序区为空；
 * 2.第i趟的排序（i=1,2,3,...,n-1）,当前有序区和无序区分别为A[1,...,i-1]和A[i,...,n],
 * 该趟从无序区选出一个最小的元素A[k],将它与无序区的第一个元素进行交换，使得A[1,...,i]和A[i+1,..,n],
 * 分别变为元素个数增加1的有序区和元素个数减少一个的无序区，
 * 3.n-1趟后，数组有序化
 * @date 2018年3月24日 下午1:08:01
 */
public class SelectionSort {

    public static int[] selectionSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length; i++) {        //快速排序每个元素都需要进行比较一次
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) {    //找到最小的元素
                    minIndex = j;                    //找到最小元素的索引，进而保存
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }

        return array;
    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] selectionSort = selectionSort(array);
        for (int i : selectionSort) {
            System.out.print(i + ",");
        }
    }
}
