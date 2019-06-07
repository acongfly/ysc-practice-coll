package com.acongfly.studyjava.javaStudy.sourceCode;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author shicongyang
 * @Description: 相关java特性学习
 * @date 2018/5/23 18:16
 */
public class UnitTest {


    public static void main(String[] args) {
        //最多是16段，线程安全
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("a", "1");
        concurrentHashMap.put("b", "2");
        concurrentHashMap.put("c", "3");
        concurrentHashMap.putIfAbsent("d", "4");
        System.out.println(concurrentHashMap.toString());

        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            copyOnWriteArrayList.add(i + "");
        }
        for (String s : copyOnWriteArrayList) {
            if (s.equals(50 + "")) {
                copyOnWriteArrayList.remove(50);
            }
        }
        System.out.println(copyOnWriteArrayList.toString());
        //演示：Exception in thread "main" java.util.ConcurrentModificationException发生，此为arrayList
        //与CopyOnWriteArrayList相关区别
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add(i + "");
        }
        for (String a : strings) {
            if (a.equals(50 + "")) {
                strings.remove(50);
            }
        }
        System.out.println(strings.toString());


        //可以解决异常问题
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals(50 + "")) {
                strings.remove(i);
                i--;
            }
        }
        System.out.println(strings.toString());


        /**
         * 是一个适用于高并发场景下的队列，通过无锁的方式，实现了一个高并发状态下的高性能。
         * 通常ConcurrentLinkedQueue性能好于BlockingQueue，它是一个基于链接节点的无界线程安全队列
         * 该队列的元素遵循先进先出的原则，头是最先加入的，尾是最近加入的，该队列不允许null元素
         */
        ConcurrentLinkedQueue<String> cLinkque = new ConcurrentLinkedQueue<>();

        ArrayBlockingQueue<String> strings1 = new ArrayBlockingQueue<String>(5);     //有界

        LinkedBlockingQueue<String> strings2 = new LinkedBlockingQueue<>();         //无界

    }


}
