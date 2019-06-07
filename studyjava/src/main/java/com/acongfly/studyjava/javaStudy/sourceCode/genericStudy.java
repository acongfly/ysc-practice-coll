package com.acongfly.studyjava.javaStudy.sourceCode;

/**
 * @author shicongyang
 * @Description: 泛型学习
 * @date 2018/5/30 16:43
 */
public class genericStudy {

    public static void main(String[] args) {
        Apple apple = new Apple();
        Person person = new Person();

        GenericTest<Fruit> generateTest = new GenericTest<Fruit>();
        //apple是Fruit的子类，所以这里可以
        generateTest.show1(apple);
        //编译器会报错，因为泛型类型实参指定的是Fruit，而传入的实参类是Person
        //generateTest.show1(person);

        //使用这两个方法都可以成功
        generateTest.show2(apple);
        generateTest.show2(person);

        //使用这两个方法也都可以成功
        generateTest.show3(apple);
        generateTest.show3(person);

        generateTest.show4("1", "2", "3");
    }


}

class Fruit {
    @Override
    public String toString() {
        return "fruit";
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "apple";
    }
}

class Person {
    @Override
    public String toString() {
        return "person";
    }
}

class GenericTest<T> {
    //这个不是泛型方法，就是一个普通的方法
    public void show1(T t) {
        System.out.println(t.toString());
    }

    //在泛型类中声明了一个泛型方法，使用泛型E，这种泛型E可以为任意类型。可以类型与T相同，也可以不同。
    //由于泛型方法在声明的时候会声明泛型<E>，因此即使在泛型类中并未声明泛型，编译器也能够正确识别泛型方法中识别的泛型。
    public <E> void show2(E e) {
        System.out.println(e.toString());
    }

    //在泛型类中声明了一个泛型方法，使用泛型T，注意这个T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
    public <T> void show3(T t) {
        System.out.println(t.toString());
    }

    //可变参数方法
    public <T> void show4(T... args) {
        for (T arg : args) {
            System.out.println("测试可变参数的值：" + arg);
        }
    }
}
