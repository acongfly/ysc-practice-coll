package com.acongfly.studyjava.javaStudy.httpClientStudy;/**
 * Created by hc on 2019/1/6.
 */

import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.impl.client.cache.BasicHttpCacheStorage;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;

/**
 * program: study<p>
 * description: httpclient  cache<p>
 * author: shicong yang<p>
 * createDate: 2019-01-06 13:23<p>
 **/

public class HttpClientCache {

    CacheConfig cacheConfig = CacheConfig.custom()
            .setMaxCacheEntries(1000)           //最多缓存1000个条目
            .setMaxObjectSize(1 * 1024 * 1024)       //缓存对象最大1M
            .setAsynchronousWorkersCore(5)       //异步更新缓存线程池最小空闲线程数
            .setAsynchronousWorkersMax(10)      //异步更新缓存线程池的最大线程数
            .setRevalidationQueueSize(10000)    //异步更新线程池队列大小
            .build();

    HttpCacheStorage cacheStorage = new BasicHttpCacheStorage(cacheConfig);

    //创建httpClient
}
