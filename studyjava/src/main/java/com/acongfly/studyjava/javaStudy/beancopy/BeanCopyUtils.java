package com.acongfly.studyjava.javaStudy.beancopy;

import java.util.*;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import net.sf.cglib.beans.BeanCopier;

/**
 * description: Bean拷贝工具类
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2018/6/28
 * <p>
 */
public class BeanCopyUtils {

    private static Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    /**
     * description: bean copy 可以包括集合的copy方式
     * <p>
     * param: [source, destinationClass]
     * <p>
     * return: T
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/10/25
     * <p>
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return MAPPER.map(source, destinationClass);
    }

    /**
     * description: bean copy 可以包括集合的copy方式
     * <p>
     * param: [source, destination]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/10/25
     * <p>
     */
    public static void map(Object source, Object destination) {
        MAPPER.map(source, destination);
    }

    /**
     * description: bean copy 可以包括集合的copy方式
     * <p>
     * param: [sourceList, destinationClass]
     * <p>
     * return: java.util.List<T>
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/10/25
     * <p>
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(MAPPER.map(sourceObject, destinationClass));
        }
        return destinationList;
    }

    /**
     * description: 实体类copy
     * <p>
     * param: [source 原实体类, target目标实体类] 不包括集合
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2018/6/28
     * <p>
     */
    public static void copyProperties(Object source, Object target) {

        Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * description: 忽略null的属性copy
     * <p>
     * param: [source, target]
     * <p>
     * return: void
     * <p>
     * author: shicong yang
     * <p>
     * date: 2019/1/17
     * <p>
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
    }

    public static void main(String[] args) {
        A a = new A();
        // a.setAge(12);
        a.setName("aaaa");
        // a.setSex("nan");
        B b = new B();
        b.setAge(12);
        b.setName("aaaa");
        b.setSex("nan");
        BeanCopyUtils.copyPropertiesIgnoreNull(a, b);
        System.out.println(b);
    }

}

@Data
class A {
    private String name;
    private int age;
    private String sex;
}

@Data
class B {
    private String name;
    private int age;
    private String sex;
}
