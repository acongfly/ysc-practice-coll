package com.acongfly.studyjava.javaStudy.guavaStudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.cache.*;

import lombok.extern.slf4j.Slf4j;

/**
 * description: guava loading cache 工具类
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2018/11/30
 * <p>
 */
@Slf4j
public class GuavaCacheUtil {
    /**
     * 缓存项最大数量
     */
    private static final long GUAVA_CACHE_SIZE = 100000;
    /**
     * 缓存时间：分钟
     */
    private static final long GUAVA_CACHE_TIME = 10;

    /**
     * 缓存操作对象
     */
    private static LoadingCache<String, Object> GLOBAL_CACHE = null;

    static {
        try {
            GLOBAL_CACHE = loadCache(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    // 该方法主要是处理缓存键不存在缓存值时的处理逻辑
                    if (log.isDebugEnabled()) {
                        log.debug("Guava Cache缓存值不存在，初始化空值，键名：{}", key);
                    }
                    put(key, key);
                    return ObjectUtils.NULL;
                }
            });
        } catch (Exception e) {
            log.error("初始化Guava Cache出错", e);
        }
    }

    /**
     * 全局缓存设置
     * <ul>
     * <li>缓存项最大数量：100000</li>
     * <li>缓存有效时间（分钟）：10</li>
     * </ul>
     *
     * @param cacheLoader
     * @return
     * @throws Exception
     */
    private static <K, V> LoadingCache<K, V> loadCache(CacheLoader<K, V> cacheLoader) throws Exception {
        /*
         * maximumSize 缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项 expireAfterAccess 表示最后一次使用该缓存项多长时间后失效 removalListener 移除缓存项时执行的逻辑方法 recordStats 开启Guava Cache的统计功能
         * expireAfterWrite设置TTL,缓存数据在给定的时间内没有写（创建/覆盖）时，则被回收，即定期会回收缓存数据
         * expireAfterAccess:设置TTI,缓存数据在给定的时间内没有被/写时，则被回收。每次访问的时候，都会更新它的TTI,从而如果该缓存是非常热的数据，则将一直不会过期，可能会导致脏数据存在很长时间，（因此建议设置expireAfterWrite）
         */
        LoadingCache<K, V> cache = CacheBuilder.newBuilder().maximumSize(GUAVA_CACHE_SIZE)
            .expireAfterWrite(GUAVA_CACHE_TIME, TimeUnit.MINUTES).removalListener(new RemovalListener<K, V>() {
                @Override
                public void onRemoval(RemovalNotification<K, V> rn) {
                    if (log.isDebugEnabled()) {
                        log.debug("Guava Cache缓存回收成功，键：{}, 值：{}", rn.getKey(), rn.getValue());
                    }
                }
            }).recordStats().build(cacheLoader);
        return cache;
    }

    /**
     * description: 设置缓存值
     * <p>
     * param: [key, value]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static void put(String key, Object value) {
        try {
            GLOBAL_CACHE.put(key, value);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("设置缓存值出错", e);
        }
    }

    /**
     * description: 批量设置缓存值
     * <p>
     * param: [map]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static void putAll(Map<? extends String, ? extends Object> map) {
        try {
            GLOBAL_CACHE.putAll(map);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("批量设置缓存值出错", e);
        }
    }

    /**
     * description: 获取缓存值 注：如果键不存在值，将调用CacheLoader的load方法加载新值到该键中
     * <p>
     * param: [key]
     * <p>
     * return: java.lang.Object
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static Object get(String key) {
        Object obj = null;
        try {
            obj = GLOBAL_CACHE.getUnchecked(key);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("获取缓存值出错", e);
        }
        return obj;
    }

    /**
     *
     *
     *
     * @param key
     * @return
     */
    /**
     * description: 获取缓存值
     * <p>
     * <p>
     * 注：如果键不存在值，将直接返回 NULL
     * </p>
     * param: [key]
     * <p>
     * return: java.lang.Object
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static Object getIfPresent(String key) {
        Object obj = null;
        try {
            obj = GLOBAL_CACHE.getIfPresent(key);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("获取缓存值出错", e);
        }
        return obj;
    }

    /**
     * description: 移除缓存
     * <p>
     * param: [key]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static void remove(String key) {
        try {
            GLOBAL_CACHE.invalidate(key);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("移除缓存出错", e);
        }
    }

    /**
     * description: 批量移除缓存
     * <p>
     * param: [keys]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static void removeAll(Iterable<String> keys) {
        try {
            GLOBAL_CACHE.invalidateAll(keys);
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("批量移除缓存出错", e);
        }
    }

    /**
     * description: 清空所有缓存
     * <p>
     * param: []
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static void removeAll() {
        try {
            GLOBAL_CACHE.invalidateAll();
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("清空所有缓存出错", e);
        }
    }

    /**
     * description: 获取缓存项数量
     * <p>
     * param: []
     * <p>
     * return: long
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static long size() {
        long size = 0;
        try {
            size = GLOBAL_CACHE.size();
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("获取缓存项数量出错", e);
        }
        return size;
    }

    /**
     * description: 获取所有缓存项的键
     * <p>
     * param: []
     * <p>
     * return: java.util.List<java.lang.String>
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static List<String> keys() {
        List<String> list = new ArrayList<String>();
        try {
            ConcurrentMap<String, Object> map = GLOBAL_CACHE.asMap();
            for (Map.Entry<String, Object> item : map.entrySet()) {
                list.add(item.getKey());
            }
            if (log.isDebugEnabled()) {
                log.debug("缓存命中率：{}，新值平均加载时间：{}", getHitRate(), getAverageLoadPenalty());
            }
        } catch (Exception e) {
            log.error("获取所有缓存项的键出错", e);
        }
        return list;
    }

    /**
     * description: 缓存命中率
     * <p>
     * param: []
     * <p>
     * return: double
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static double getHitRate() {
        return GLOBAL_CACHE.stats().hitRate();
    }

    /**
     * description: 加载新值的平均时间，单位为纳秒
     * <p>
     * param: []
     * <p>
     * return: double
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static double getAverageLoadPenalty() {
        return GLOBAL_CACHE.stats().averageLoadPenalty();
    }

    /**
     * description: 缓存项被回收的总数，不包括显式清除
     * <p>
     * param: []
     * <p>
     * return: long
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/11/30
     * <p>
     */
    public static long getEvictionCount() {
        return GLOBAL_CACHE.stats().evictionCount();
    }

    public static void main(String[] args) {
        Object zhangsan = get("zhangsan");
        if (Objects.equals(zhangsan, ObjectUtils.NULL)) {
            System.out.println("1234567");
        }
        System.out.println(zhangsan);
        Object zhangsan1 = get("zhangsan");
        System.out.println(zhangsan1);

    }
}