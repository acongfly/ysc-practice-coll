package com.acongfly.studyjava.javaStudy.functionStudy;

import java.util.function.Function;

/**
 * @program: study
 * @description: Function学习
 * @author: shicong yang
 * @create: 2019-04-09 18:23
 **/
public class FunctionStudy {

    /**
     * name type description Consumer Consumer< T > 接收T对象，不返回值 Predicate Predicate< T > 接收T对象并返回boolean Function
     * Function< T, R > 接收T对象，返回R对象 Supplier Supplier< T > 提供T对象（例如工厂），不接收值 UnaryOperator UnaryOperator< T > 接收T对象，返回T对象
     * BiConsumer BiConsumer<T, U> 接收T对象和U对象，不返回值 BiPredicate BiPredicate<T, U> 接收T对象和U对象，返回boolean BiFunction
     * BiFunction<T, U, R> 接收T对象和U对象，返回R对象 BinaryOperator BinaryOperator< T > 接收两个T对象，返回T对象
     */

    /**
     * 可以参照 y=f(x), function的两个参数就是类似于x,y，前一个数相当于x,后面一个相当于y
     */

    // 将Function对象应用到输入的参数上，然后返回计算结果。
    // R apply(T t);

    // 返回一个先执行当前函数对象apply方法再执行after函数对象apply方法的函数对象。
    // default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
    // Objects.requireNonNull(after);
    // return (T t) -> after.apply(apply(t));
    // }

    // 返回一个先执行before函数对象apply方法再执行当前函数对象apply方法的函数对象
    // default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
    // Objects.requireNonNull(before);
    // return (V v) -> apply(before.apply(v));
    // }

    /**
     * compose 和 andThen 的不同之处是函数执行的顺序不同。compose 函数先执行参数，然后执行调用者，而 andThen 先执行调用者，然后再执行参数。
     */

    public static void main(String[] args) {
        Function<Integer, Integer> name = e -> e * 2;
        Function<Integer, Integer> square = e -> e * e;
        int value = name.andThen(square).apply(3);
        System.out.println("andThen value=" + value);
        int value2 = name.compose(square).apply(3);
        System.out.println("compose value2=" + value2);
        // 返回一个执行了apply()方法之后只会返回输入参数的函数对象
        Object identity = Function.identity().apply("huohuo");
        System.out.println(identity);
    }
}
