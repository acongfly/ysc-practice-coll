package com.acongfly.studyjava.javaStudy.jdk8Study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author shicongyang
 * @ClassName: NewFeaturesStudy
 * @Description: jdk8新特性学习
 * @date 2018年6月28日 下午8:38:04
 */
public class NewFeaturesStudy {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("zhangsan  ");
        arrayList.add("lisi  ");
        arrayList.add("wangwu  ");
        arrayList.add("liuqi  ");
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("zhangsan  ");
        arrayList2.add("lisi  ");
        arrayList2.add("wangwu  ");
        arrayList2.add("liuqi  ");

        System.out.println("java7 排序结果");
        NewFeaturesStudy newFeaturesStudy = new NewFeaturesStudy();
        newFeaturesStudy.sortUsingJava7(arrayList);
        System.out.println(arrayList);
        System.out.println("java8 排序结果");
        newFeaturesStudy.sortUsingJava8(arrayList2);
        System.out.println(arrayList2);
    }

    /**
     * @param @param names
     * @return void
     * @throws
     * @Title: sortUsingJava7
     * @Description: 使用java7排序
     * @author shicongyang
     */
    private void sortUsingJava7(List<String> names) {
        Collections.sort(names, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * @param @param names
     * @return void
     * @throws
     * @Title: sortUsingJava8
     * @Description: 使用java8排序
     * @author shicongyang
     */
    private void sortUsingJava8(List<String> names) {
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
    }

}
