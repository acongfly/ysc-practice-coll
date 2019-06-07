package com.acongfly.studyjava.javaStudy.rxJavaStudy;

import com.google.common.collect.Maps;
import rx.Observable;
import rx.Subscriber;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: study
 * @description:
 * @author: shicong yang
 * @create: 2019-05-05 20:53
 **/
public class RxjavaStudy02 {

    public static final ConcurrentMap<String, Integer> map = Maps.newConcurrentMap();

    public static void main(String[] args) {
        subscribe();
    }

    public static int getRandom(int num) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(num);
    }


    public static void subscribe() {
        Integer[] number = {1, 2, 3, 4};
        List<Integer> integers = Arrays.asList(number);

        //1.创建一个被观察者，被观察者从list集合获取数据，等人来订阅
        Observable<Integer> observable = Observable.from(integers);
        //2.创建一个观察者，subscriber是observer的实现类，所以也是一个观察者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                //被观察者发送完数据会调用改方法
                System.out.println("RX-->>onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                //被观察者传输数据中发生异常会调用该方法
                System.out.println("RX-->>onError");
                System.out.println(e.getMessage());

            }


            @Override
            public void onNext(Integer integer) {
                //被观察者发送的数据都会送到这里
                System.out.println("RX-->>onNext" + integer);
                System.out.println("RX-->>rx1:" + map.get("rx1"));
                System.out.println("RX-->>rx2:" + map.get("rx2"));
                System.out.println("RX-->>rx3:" + map.get("rx3"));
                System.out.println("RX-->>rx4:" + map.get("rx4"));
//                throw new BizException("","2000|我错了");
            }

            @Override
            public void onStart() {
                //这个方法是在观察者和被观察者已连接，但是被观察者还没有向观察者发送数据时进行调用,就是初始化时候使用的
                System.out.println("RX-->>onStart");
                map.put("rx1", getRandom(1234));
                map.put("rx2", getRandom(1234));
                map.put("rx3", getRandom(1234));
                map.put("rx4", getRandom(1234));

            }
        };
        // 3.订阅
        //      正常来说应该是：observer.subscribe(observable); 看起来更合乎逻辑
        //      这样反而像是：被观察者 订阅了 观察者(报纸 订阅了 读者)
        //      这涉及到流式编程，姑且先这样记住吧
        observable.subscribe(subscriber);
    }
}
