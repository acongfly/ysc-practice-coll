package com.acongfly.studyjava.javaStudy.rxJavaStudy;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import lombok.Data;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * program: study
 * <p>
 * description: Rxjava学习
 * <p>
 * author: shicong yang
 * <p>
 * createDate: 2018-12-18 09:41
 * <p>
 **/

public class RxjavaStudy {
    // https://www.jianshu.com/p/0774f8904217

    public static final ConcurrentMap<String, Integer> map = Maps.newConcurrentMap();

    public static int getRandom(int num) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(num);
    }

    public static void main(String[] args) {
        // subscribe(); //观察者
        // Observable(); //被观察者

        People[] people = new People[] {new People("战三", 18, new String[] {"睡觉", "吃饭", "玩"}),
            new People("战四", 20, new String[] {"敲代码", "写代码", "调代码"})};
        // 观察者
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String name) {
                System.out.println("接受信息：" + name);
            }
        };

        // map可以看到被观察者从People数组里读取每一个元素
        // 在map方法里找到每一个元素对象的name并传递给观察者
        // 观察者接收并使用
        // 这里转换范围很大，不仅仅只是提取属性。
        Observable.from(people).map(new Func1<People, String>() {
            @Override
            public String call(People people) {
                return people.getName();
            }
        }).subscribe(subscriber);
        // flatMap效果和map是类似的
        // 区别在于map是用于一对一，而flatMap是用于一对多
        // 被观察者从People数组读取每一个对象，call()里读取每一个对象的hobby属性，并依次返回其中的一个元素
        // Observable.from(people).flatMap(new Func1<People, Observable<String>>() {
        // @Override
        // public Observable<String> call(People people) {
        // return Observable.from(people.getLoves());
        // }
        // }).subscribe(subscriber);

        // 线程
        // RxJava遵循的线程原则：在那个线程订阅，则被观察者和观察者的操作都在该线程。
        // 通过Schedulers切换线程。
        // Schedulers.immediate()：默认值。在当前线程运行。
        // AndroidSchedulers.mainThread()：在Android主线程运行。
        // 注意：这个是RxAndroid里的。必须要导入RxAndroid的jar包。RxJava里是没有的。
        // Schedulers.newThread()：总是开启新线程运行。
        // Schedulers.io()：如果操作涉及到I/O使用该项。
        // 也是总是开启新线程运行
        // 内部有线程池和复用
        // Schedulers.computation()：如果操作涉及到图形计算等使用该项。

        // 被观察者在新开起的IO线程做读取/过滤/转换操作
        // 数据传给观察者
        // 观察者在当前线程显示数据
        // Observable.from(people).filter(new Func1<People, Boolean>() {
        // @Override
        // public Boolean call(People people) {
        // return people.getAge()>18;
        // }
        // }).map(new Func1<People, String>() {
        // @Override
        // public String call(People people) {
        // return people.getName();
        // }
        // }).subscribeOn(Schedulers.immediate()) //当前线程
        // .observeOn(Schedulers.io()) //io线程
        // .subscribe(subscriber);

    }

    /**
     * 被观察者
     */
    private static void Observable() {
        // 观察者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("接收数据结束");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer t) {
                System.out.println("接收数据：" + t);
            }
        };

        // 第一种
        // 被观察者
        // 作用：作为数据的发送方，它决定什么时候发送，怎么发送
        // Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
        // @Override
        // public void call(Subscriber<? super Integer> subscriber) {
        // subscriber.onNext(1);
        // subscriber.onNext(2);
        // subscriber.onNext(3);
        // subscriber.onNext(4);
        // subscriber.onCompleted();
        // }
        // });
        // 第二种 从一个数组或Iterable中依次发送数据元素
        // Integer[] num = {1,2,3};
        // Observable<Integer> observable = Observable.from(num);

        // 第三种 将参数依次发送过来。
        Observable observable = Observable.just(1, 2, 3);
        observable.subscribe(observer);
    }

    /**
     * description: 观察者 作用：接收数据并进行处理
     * <p>
     * param: []
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/12/20
     * <p>
     */
    private static void subscribe() {
        Integer[] number = {1, 2, 3, 4};
        List<Integer> integers = Arrays.asList(number);

        // 1.创建一个被观察者，被观察者从list集合获取数据，等人来订阅
        Observable<Integer> observable = Observable.from(integers);
        // 2.创建一个观察者，subscriber是observer的实现类，所以也是一个观察者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                // 被观察者发送完数据会调用改方法
                System.out.println("RX-->>onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                // 被观察者传输数据中发生异常会调用该方法
                System.out.println("RX-->>onError");
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                // 被观察者发送的数据都会送到这里
                System.out.println("RX-->>onNext" + integer);
                System.out.println("RX-->>rx1:" + map.get("rx1"));
                System.out.println("RX-->>rx2:" + map.get("rx2"));
                System.out.println("RX-->>rx3:" + map.get("rx3"));
                System.out.println("RX-->>rx4:" + map.get("rx4"));
            }

            @Override
            public void onStart() {
                // 这个方法是在观察者和被观察者已连接，但是被观察者还没有向观察者发送数据时进行调用,就是初始化时候使用的
                System.out.println("RX-->>onStart");
                map.put("rx1", getRandom(1234));
                map.put("rx2", getRandom(1234));
                map.put("rx3", getRandom(1234));
                map.put("rx4", getRandom(1234));
            }
        };
        // 3.订阅
        // 正常来说应该是：observer.subscribe(observable); 看起来更合乎逻辑
        // 这样反而像是：被观察者 订阅了 观察者(报纸 订阅了 读者)
        // 这涉及到流式编程，姑且先这样记住吧
        observable.subscribe(subscriber);
    }

    @Data
    static class People {

        private String name;
        private int age;
        private String[] loves;

        public People(String name, int age, String[] loves) {
            this.name = name;
            this.age = age;
            this.loves = loves;

        }

    }
}
