package com.acongfly.studyjava.algorithm;

/**
 * @author shicongyang
 * @ClassName: HeapSort
 * @Description: 堆排序 平均时间复杂度：O(nlogn) 最好情况：O(nlogn) 最坏情况：O(nlogn) 空间复杂度：O(1) 占用常数内存，不占用额外内存 是不稳定排序方式 算法的基本描述：
 *               堆积近似是一个完全二叉树的结构： 1.将初始待排序关键字的无序的序列（A1,A2,A3,...,An）构建成大顶锥，现在这堆为无序的；
 *               2.将堆顶元素A[1]与最后一个元素A[n]交换，此时得到的是一个新的无序区(A1,A2,A3,...,An-1)和 新的有序区An,切满足A[1,2,3,...,n-1]<=A[n];
 *               3.由于交换后的堆顶A[1],可能违反堆的性质，所以需要对当前无序区（A1,A2,...,An-1）调整为新堆，然后
 *               再次将A[1]与无序区最后一个元素交换，得到新的无序区（A1,A2,...,An-2）和新的有序区（An-1,An）,不断重复此过程直到有序区 的元素个数为n-1，那么整个排序过程完成
 * @date 2018年3月28日 下午9:57:12
 */
public class HeapSort {

    // 声明全局变量，用于记录数组array的长度；
    static int len;

    public static int[] heapSort(int[] array) {
        len = array.length;
        if (len < 0) {
            return array;
        }
        // 1.构建一个最大的堆
        buildMaxHeap(array);
        // 2.循环将首位的最大值和末尾的值进行交换，然后再次调整最大堆
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }
        return array;
    }

    /**
     * @param array
     * @param i
     * @Description: 调整使之成为最大堆
     * @author shicongyang
     * @date 2018年4月11日 下午1:53:25
     */
    public static void adjustHeap(int[] array, int i) {
        int maxIndex = i;
        // 如果有左子树，并且左子树大于父节点，则将最大指针指向左子树
        if (i * 2 < len && array[i * 2] > array[maxIndex]) {
            maxIndex = i * 2;
        }
        // 如果有右子树，并且右子树大于父节点，则将最大指针指向右子树
        if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex]) {
            maxIndex = i * 2 + 1;
        }
        // 如果父节点不是最大值，则将父节点与最大值进行交换，并且递归调整与父节点进行交换的位置
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex);
        }

    }

    /**
     * @param array
     * @param i
     * @param j
     * @Description: 交换数值
     * @author shicongyang
     * @date 2018年4月11日 下午1:48:28
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * @param array
     * @Description: 建立最大堆
     * @author shicongyang
     * @date 2018年4月11日 下午1:37:23
     */
    public static void buildMaxHeap(int[] array) {
        // 从最后一个非叶子节点开始向上构建
        for (int i = (len / 2 - 1); i >= 0; i--) {
            adjustHeap(array, i);
        }

    }

    public static void main(String[] args) {
        int[] array = {2, 4, 3, 9, 6, 5, 4, 0};
        int[] heapSort = heapSort(array);
        for (int i : heapSort) {
            System.out.print(i + ",");
        }
    }

}
