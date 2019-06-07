package com.acongfly.studyjava.javaStudy.comparStudy;

import java.util.Comparator;

/**
 * @author shicongyang
 * @Description: Comparator
 * <p>
 * Comparator可以认为是是一个外比较器，有两种情况可以使用实现Comparator接口的方式：
 * 1、一个对象不支持自己和自己比较（没有实现Comparable接口），但是又想对两个对象进行比较
 * 2、一个对象实现了Comparable接口，但是开发者认为compareTo方法中的比较方式并不是自己想要的那种比较方式
 * <p>
 * Comparator接口里面有一个compare方法，方法有两个参数T o1和T o2，是泛型的表示方式，分别表示待比较的两个对象，方法返回值和Comparable接口一样是int，有三种情况：
 * <p>
 * 1、o1大于o2，返回正整数
 * <p>
 * 2、o1等于o2，返回0
 * <p>
 * 3、o1小于o3，返回负整数
 * @date 2018年4月25日 下午2:37:26
 */
public class comparatorStudy implements Comparator<people> {

    @Override
    public int compare(people o1, people o2) {
        if (o1.getName().compareTo(o2.getName()) > 0) {
            return 1;
        } else if (o1.getName().compareTo(o2.getName()) == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        people a = new people("a");
        people b = new people("b");
        people c = new people("c");
        comparatorStudy comparatorStudy = new comparatorStudy();
        System.out.println(comparatorStudy.compare(a, a));
        System.out.println(comparatorStudy.compare(a, b));
        System.out.println(comparatorStudy.compare(c, b));
    }
}

class people {

    private String name;

    public people(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}