package com.acongfly.studyjava.javaStudy.guavaStudy;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * program: study
 * <p>
 * description: 大数中去判断数据是否存在
 * <p>
 * 使用Bloom Filter 原理 所以布隆过滤有以下几个特点： 只要返回数据不存在，则肯定不存在。 返回数据存在，但只能是大概率存在。 同时不能清除其中的数据。 使用guava实现 author: shicong yang
 * <p>
 * createDate: 2018-12-05 16:15
 * <p>
 **/

public class LargeNumStudy {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(start);
        // 量：100000 失败率：0.0001
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 100000, 0.0001);
        for (int i = 0; i < 100000000; i++) {
            filter.put(i);
        }
        System.out.println(filter.mightContain(2));
        System.out.println(filter.mightContain(100000));
        System.out.println(filter.mightContain(10000000));
        System.out.println(filter.mightContain(1000000000));
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("执行时间：" + (end - start));
    }
}
