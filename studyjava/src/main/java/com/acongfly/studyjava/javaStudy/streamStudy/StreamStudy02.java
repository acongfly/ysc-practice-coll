package com.acongfly.studyjava.javaStudy.streamStudy;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: study
 * @description:
 * @author: shicong yang
 * @create: 2019-06-06 15:46
 **/
public class StreamStudy02 {
    /**
     * 并发 stream替换成parallelStream或 parallel 输入流的大小并不是决定并行化是否会带来速度提升的唯一因素，性能还会受到编写代码的方式和核的数量的影响
     * 影响性能的五要素是:数据大小、源数据结构、值是否装箱、可用的CPU核数量，以及处理每个元素所花的时间
     */

    // 根据数字的大小，有不同的结果
    private static int size = 10000000;

    public static void main(String[] args) {
        System.out.println("-----------List-----------");
        testList();
        System.out.println("-----------Set-----------");
        testSet();
    }

    /**
     * 测试list
     */
    public static void testList() {
        List<Integer> list = new ArrayList<>(size);
        for (Integer i = 0; i < size; i++) {
            list.add(new Integer(i));
        }

        List<Integer> temp1 = new ArrayList<>(size);
        // 老的
        long start = System.currentTimeMillis();
        for (Integer i : list) {
            temp1.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);

        // 同步
        long start1 = System.currentTimeMillis();
        list.stream().collect(toList());
        System.out.println(System.currentTimeMillis() - start1);

        // 并发
        long start2 = System.currentTimeMillis();
        list.parallelStream().collect(toList());
        System.out.println(System.currentTimeMillis() - start2);
    }

    /**
     * 测试set
     */
    public static void testSet() {
        List<Integer> list = new ArrayList<>(size);
        for (Integer i = 0; i < size; i++) {
            list.add(new Integer(i));
        }

        Set<Integer> temp1 = new HashSet<>(size);
        // 老的
        long start = System.currentTimeMillis();
        for (Integer i : list) {
            temp1.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);

        // 同步
        long start1 = System.currentTimeMillis();
        list.stream().collect(Collectors.toSet());
        System.out.println(System.currentTimeMillis() - start1);

        // 并发
        long start2 = System.currentTimeMillis();
        list.parallelStream().collect(Collectors.toSet());
        System.out.println(System.currentTimeMillis() - start2);
    }

    /**
     * 通过peek可以查看每个值，同时能继续操作流
     */
    private static void peekTest() {
        List<PersonModel> data = Data.getData();

        // peek打印出遍历的每个per
        data.stream().map(per -> per.getName()).peek(p -> {
            System.out.println(p);
        }).collect(toList());
    }
}
