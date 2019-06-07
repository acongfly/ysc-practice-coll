package com.acongfly.studyjava.javaStudy.guavaStudy;/**
 * Created by hc on 2018/12/15.
 */

import com.google.common.util.concurrent.RateLimiter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * program: study<p>
 * description: 限流操作<p>
 * author: shicong yang<p>
 * createDate: 2018-12-15 23:12<p>
 **/

public class RateLimiterStudy {


    //令牌桶算法限流，使用的是均值操作
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(5, 1000, TimeUnit.MILLISECONDS);


    public static void main(String[] args) {
        System.out.println(RATE_LIMITER.acquire());
        System.out.println(RATE_LIMITER.acquire());
        System.out.println(RATE_LIMITER.acquire());
        System.out.println(RATE_LIMITER.acquire(100));
        System.out.println(RATE_LIMITER.acquire());
        System.out.println(RATE_LIMITER.acquire());
        System.out.println(RATE_LIMITER.acquire());
    }

}
