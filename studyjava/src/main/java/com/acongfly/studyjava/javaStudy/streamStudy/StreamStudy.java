package com.acongfly.studyjava.javaStudy.streamStudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hc on 2018/8/1.
 */
public class StreamStudy {
    public static void main(String[] args) {
        // List<Integer> list = Lists.newArrayList();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(5);
        list.add(3);
        list.add(6);
        System.out.print("list中的元素：");
        for (Integer ele : list) {
            System.out.print(ele + "  ");
        }
        System.out.println();

        Map<Integer, List<Integer>> collect1 = list.stream().collect(Collectors.groupingBy(elem -> elem % 2));
        System.out.println(collect1);

        // 计算最小值
        System.out.print("list中最小值");
        Stream<Integer> stream = list.stream();
        Optional<Integer> min = stream.min(Integer::compareTo);
        if (min.isPresent()) {
            System.out.print(min.get());
        }
        System.out.println();
        // 计算最大值
        System.out.print("计算最大值");
        list.stream().max(Integer::compareTo).ifPresent(System.out::print);
        // 排序
        System.out.println();
        System.out.println("将list进行排序");
        Stream<Integer> sorted = list.stream().sorted();
        sorted.forEach(elem -> System.out.print(elem + " "));
        System.out.println();

        // 过滤
        System.out.println("过滤：选择大于3的元素");
        list.stream().filter(elem -> elem > 3).forEach(elem -> System.out.print(elem + " "));
        System.out.println();
        // 过滤
        System.out.println("过滤List流,只剩下那些大于0并且小于4的元素:");
        list.stream().filter(elem -> elem > 0).filter(elem -> elem < 4).sorted(Integer::compareTo)
            .forEach(elem -> System.out.print(elem + " "));

        System.out.println();
        // 求和
        ArrayList<Long> list2 = new ArrayList<>();
        list2.add(1L);
        list2.add(4L);
        list2.add(2L);
        list2.add(5L);
        list2.add(3L);
        list2.add(6L);
        Long reduce = list2.stream().reduce(0L, (x, y) -> x + y);
        System.out.println("==========求和：" + reduce);

        long sum = list2.stream().mapToLong((x) -> x).sum();
        System.out.println("==========求和2：" + reduce);

        List<Integer> collect = list.stream().filter(elem -> elem > 3).collect(Collectors.toList());
        System.out.println("------collect=" + collect);

        TestEntity testEntity = new TestEntity();
        testEntity.setMoney(1L);
        testEntity.setName("aaa");
        testEntity.setSex("a");
        List<TestEntity> testEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testEntities.add(testEntity);
        }
        long sum1 = testEntities.stream().map(TestEntity::getMoney).mapToLong((x) -> x).sum();
        System.out.println("======求和sum1:" + sum1);

        List<String> strings = new ArrayList<>();
        ArrayList<String> strings1 = new ArrayList<>();
        ArrayList<String> strings2 = new ArrayList<>();
        ArrayList<String> strings3 = new ArrayList<>();
        strings.add("1111");
        strings1.add("2222");
        strings2.add("3333");
        strings3.addAll(strings);
        strings3.addAll(strings1);
        strings3.addAll(strings2);
        System.out.println("=======addall=" + strings3);

        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Integer> list4 = new ArrayList<>();
        list4.add(3);
        list4.add(4);
        list4.add(5);

        System.out.println("====求交集===");

        List<Integer> lista = list1.stream().filter(t -> list4.contains(t)).collect(Collectors.toList());
        lista.stream().forEach(System.out::println);

        System.out.println("====求差集===");
        lista = list1.stream().filter(t -> !list4.contains(t)).collect(Collectors.toList());
        lista.stream().forEach(System.out::println);

        System.out.println("====求并集===");

        list.addAll(list1);
        list.addAll(list4);
        lista = list.stream().distinct().collect(Collectors.toList());
        lista.stream().forEach(System.out::println);

    }

    public static class TestEntity {
        private Long money;
        private String name;
        private String sex;

        public Long getMoney() {
            return money;
        }

        public void setMoney(Long money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
