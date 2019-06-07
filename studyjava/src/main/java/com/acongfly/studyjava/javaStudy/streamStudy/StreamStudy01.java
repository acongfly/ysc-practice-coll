package com.acongfly.studyjava.javaStudy.streamStudy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @program: study
 * @description: Stream学习(https : / / www.jianshu.com / p / 9fe8632d0bc2)
 * @author: shicong yang
 * @create: 2019-06-06 15:22
 **/
public class StreamStudy01 {
    /**
     * 过滤所有的男性
     */
    public static void fiterSex() {
        List<PersonModel> data = Data.getData();

        //old
        List<PersonModel> temp = new ArrayList<>();
        for (PersonModel person : data) {
            if ("男".equals(person.getSex())) {
                temp.add(person);
            }
        }
        System.out.println(temp);
        //new
        List<PersonModel> collect = data
                .stream()
                .filter(person -> "男".equals(person.getSex()))
                .collect(toList());
        System.out.println(collect);
    }

    /**
     * 过滤所有的男性 并且小于20岁
     */
    public static void fiterSexAndAge() {
        List<PersonModel> data = Data.getData();

        //old
        List<PersonModel> temp = new ArrayList<>();
        for (PersonModel person : data) {
            if ("男".equals(person.getSex()) && person.getAge() < 20) {
                temp.add(person);
            }
        }

        //new 1
        List<PersonModel> collect = data
                .stream()
                .filter(person -> {
                    if ("男".equals(person.getSex()) && person.getAge() < 20) {
                        return true;
                    }
                    return false;
                })
                .collect(toList());
        //new 2
        List<PersonModel> collect1 = data
                .stream()
                .filter(person -> ("男".equals(person.getSex()) && person.getAge() < 20))
                .collect(toList());

    }

    /**
     * Map
     * map生成的是个一对一映射,for的作用
     * 比较常用
     * 而且很简单
     */
    /**
     * 取出所有的用户名字
     */
    public static void getUserNameList() {
        List<PersonModel> data = Data.getData();

        //old
        List<String> list = new ArrayList<>();
        for (PersonModel persion : data) {
            list.add(persion.getName());
        }
        System.out.println(list);

        //new 1
        List<String> collect = data.stream().map(person -> person.getName()).collect(toList());
        System.out.println(collect);

        //new 2
        List<String> collect1 = data.stream().map(PersonModel::getName).collect(toList());
        System.out.println(collect1);

        //new 3
        List<String> collect2 = data.stream().map(person -> {
            System.out.println(person.getName());
            return person.getName();
        }).collect(toList());
    }

    /**
     * 顾名思义，跟map差不多,更深层次的操作
     * 但还是有区别的
     * map和flat返回值不同
     * Map 每个输入元素，都按照规则转换成为另外一个元素。
     * 还有一些场景，是一对多映射关系的，这时需要 flatMap。
     * Map一对一
     * Flatmap一对多
     * map和flatMap的方法声明是不一样的
     * <r> Stream<r>      map(Function mapper);
     * <r> Stream<r> flatMap(Function> mapper);
     * map和flatMap的区别：我个人认为，flatMap的可以处理更深层次的数据，入参为多个list，结果可以返回为一个list，而map是一对一的，入参是多个list，结果返回必须是多个list。通俗的说，如果入参都是对象，那么flatMap可以操作对象里面的对象，而map只能操作第一层。
     */
    public static void flatMapString() {
        List<PersonModel> data = Data.getData();
        //返回类型不一样
        List<String> collect = data.stream()
                .flatMap(person -> Arrays.stream(person.getName().split(" "))).collect(toList());

        List<Stream<String>> collect1 = data.stream()
                .map(person -> Arrays.stream(person.getName().split(" "))).collect(toList());

        //用map实现
        List<String> collect2 = data.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(Arrays::stream).collect(toList());
        //另一种方式
        List<String> collect3 = data.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(str -> Arrays.asList(str).stream()).collect(toList());
    }


    /**
     * Reduce
     * 感觉类似递归
     * 数字(字符串)累加
     */

    public static void reduceTest() {
        //累加，初始化值是 10
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) -> {
                    System.out.println("count:" + count);
                    System.out.println("item:" + item);
                    return count + item;
                });
        System.out.println(reduce);

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce1);

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("0", (x, y) -> (x + "," + y));
        System.out.println(reduce2);
    }

    /**
     * toList
     */
    public static void toListTest() {
        List<PersonModel> data = Data.getData();
        List<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toList());
    }

    /**
     * toSet
     */
    public static void toSetTest() {
        List<PersonModel> data = Data.getData();
        Set<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toSet());
    }

    /**
     * toMap
     */
    public static void toMapTest() {
        List<PersonModel> data = Data.getData();
        Map<String, Integer> collect = data.stream()
                .collect(
                        Collectors.toMap(PersonModel::getName, PersonModel::getAge)
                );

        data.stream()
                .collect(Collectors.toMap(per -> per.getName(), value -> {
                    return value + "1";
                }));
    }

    /**
     * 指定类型
     */
    public static void toTreeSetTest() {
        List<PersonModel> data = Data.getData();
        TreeSet<PersonModel> collect = data.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
    }

    /**
     * 分组
     */
    public static void toGroupTest() {
        List<PersonModel> data = Data.getData();
        Map<Boolean, List<PersonModel>> collect = data.stream()
                .collect(Collectors.groupingBy(per -> "男".equals(per.getSex())));
        System.out.println(collect);
    }

    /**
     * 分隔
     */
    public static void toJoiningTest() {
        List<PersonModel> data = Data.getData();
        String collect = data.stream()
                .map(personModel -> personModel.getName())
                .collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect);
    }

    /**
     * 自定义
     */
    public static void reduce() {
        List<String> collect = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect);
    }

    /**
     * Optional
     * <p>
     * Optional 是为核心类库新设计的一个数据类型，用来替换 null 值。
     * 人们对原有的 null 值有很多抱怨，甚至连发明这一概念的Tony Hoare也是如此，他曾说这是自己的一个“价值连城的错误”
     * 用处很广，不光在lambda中，哪都能用
     * Optional.of(T)，T为非空，否则初始化报错
     * Optional.ofNullable(T)，T为任意，可以为空
     * isPresent()，相当于 ！=null
     * ifPresent(T)， T可以是一段lambda表达式 ，或者其他代码，非空则执行
     */

    public static void main(String[] args) {


//        PersonModel personModel=new PersonModel();

        //对象为空则打出 -
//        Optional<Object> o = Optional.of(personModel);
//        System.out.println(o.isPresent()?o.get():"-");
//
//        //名称为空则打出 -
//        Optional<String> name = Optional.ofNullable(personModel.getName());
//        System.out.println(name.isPresent()?name.get():"-");

        //如果不为空，则打出xxx
        Optional.ofNullable("test").ifPresent(na -> {
            System.out.println(na + "ifPresent");
        });

        //如果空，则返回指定字符串
        System.out.println(Optional.ofNullable(null).orElse("-"));
        System.out.println(Optional.ofNullable("1").orElse("-"));

        //如果空，则返回 指定方法，或者代码
        System.out.println(Optional.ofNullable(null).orElseGet(() -> {
            return "hahah";
        }));
        System.out.println(Optional.ofNullable("1").orElseGet(() -> {
            return "hahah";
        }));

        //如果空，则可以抛出异常
//        System.out.println(Optional.ofNullable("1").orElseThrow(()->{
//            throw new RuntimeException("ss");
//        }));


//        Objects.requireNonNull(null,"is null");


        //利用 Optional 进行多级判断
//        EarthModel earthModel1 = new EarthModel();
//        //old
//        if (earthModel1!=null){
//            if (earthModel1.getTea()!=null){
//                //...
//            }
//        }
//        //new
//        Optional.ofNullable(earthModel1)
//                .map(EarthModel::getTea)
//                .map(TeaModel::getType)
//                .isPresent();
//
//
////        Optional<EarthModel> earthModel = Optional.ofNullable(new EarthModel());
////        Optional<List<PersonModel>> personModels = earthModel.map(EarthModel::getPersonModels);
////        Optional<Stream<String>> stringStream = personModels.map(per -> per.stream().map(PersonModel::getName));
//
//
//        //判断对象中的list
//        Optional.ofNullable(new EarthModel())
//                .map(EarthModel::getPersonModels)
//                .map(pers->pers
//                        .stream()
//                        .map(PersonModel::getName)
//                        .collect(toList()))
//                .ifPresent(per-> System.out.println(per));
//
//
//        List<PersonModel> models=Data.getData();
//        Optional.ofNullable(models)
//                .map(per -> per
//                        .stream()
//                        .map(PersonModel::getName)
//                        .collect(toList()))
//                .ifPresent(per-> System.out.println(per));

    }
}
