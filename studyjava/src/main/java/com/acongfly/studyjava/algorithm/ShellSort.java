package com.acongfly.studyjava.algorithm;

/**
 * @author shicongyang
 * @ClassName: ShellSort
 * @Description: 希尔排序 平均的时间复杂度是O(nlogn) 最好情况O(nlog²n) 最坏情况O(nlog²n) 空间复杂度O(1) 占用常数内存，不占用额外内存 不稳定排序
 *               希尔排序是记录按下表的一定的增量进行分组，对每组使用直接插入的排序方式，随着增量逐渐减少， 每组包含的元素是越来越多，最后当增量是变为1的时候，整个文件被分成一组，算法就被终止了：
 *               此算法中使用的增量为incr = length/2,缩小增量继续以incr = incr/2的方式；（此为希尔常量）
 *               <p>
 *               算法的简单描述： 1.选择一个增量序列，t1,t2,...,tk,其中ti>tj,tk=1; 2.按照增量序列的个数k，对序列进行k趟排序；
 *               3.每趟排序，根据对应的增量ti,将待排序的序列进行分割，分割成若干长度为m的子序列， 分别对各子表进行直接的插入排序。仅增量因子为1的时候，整个序列作为一个表进行处理，表长度就是整个序列的长度
 * @date 2018年3月25日 下午7:27:09
 */
public class ShellSort {

    public static int[] shellSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int len = array.length;
        int temp, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap; // 等价于 preIndex - gap = preIndex
                }
                array[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        return array;
    }

    public static void main(String[] args) {

        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] insertionSort = shellSort(array);
        for (int i : insertionSort) {
            System.out.print(i + ",");
        }

    }

}
