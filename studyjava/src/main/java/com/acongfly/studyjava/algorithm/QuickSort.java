package com.acongfly.studyjava.algorithm;

/**
 * @author shicongyang
 * @ClassName: QuickSort
 * @Description: 快速排序
 * 平均时间复杂度：O(nlogn)
 * 最好情况：O(nlogn)
 * 最坏情况：O(n²)
 * 空间复杂度O(logn)
 * 占用常数内存，不占用额外的内存
 * 是不稳定排序
 * 算法的简单描述;
 * 快速排序也是使用的是分治法，将一个串分成两个子串
 * 1.从数列中挑出一个元素，此元素作为基准元素
 * 2.重新排序数列，所有元素比基准元素小的放在基准元素前面，所有元素比基准元素大的放在
 * 基准元素后面，如果相同可以放在任意一边，在这个分区退出后，该基准就处于数列的中间位置
 * 这个就是分区操作
 * 3。递归的将小于基准值的元素的子数列和大于基准值元素的子数列排序
 * @date 2018年3月28日 下午9:39:37
 */
public class QuickSort {

    /**
     * @param @param  array
     * @param @param  start
     * @param @param  end
     * @param @return
     * @return int[]
     * @throws
     * @Title: quickSort
     * @Description: 快速排序算法
     * @author shicongyang
     */
    public static int[] quickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end < 0 || start > end) {
            return null;
        }
        int smallIndex = partition(array, start, end);
        if (smallIndex > start) {
            quickSort(array, start, smallIndex - 1);
        }
        if (smallIndex < end) {
            quickSort(array, smallIndex + 1, end);
        }
        return array;
    }

    public static int partition(int[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        //交换组内两个元素
        swap(array, pivot, end);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex) {
                    //交换组内两个元素
                    swap(array, i, smallIndex);
                }
            }

        }
        return smallIndex;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] quickSort = quickSort(array, 0, array.length - 1);
        for (int i : quickSort) {
            System.out.print(i + ",");
        }
    }

}
