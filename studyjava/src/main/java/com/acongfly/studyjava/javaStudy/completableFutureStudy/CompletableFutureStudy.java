package com.acongfly.studyjava.javaStudy.completableFutureStudy;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: ysc-practice-coll
 * @description: https://colobu.com/2016/02/29/Java-CompletableFuture/
 * @author: shicong yang
 * @create: 2020-03-16 11:41
 **/
public class CompletableFutureStudy {

    private static Random random = new Random();
    private static long t = System.currentTimeMillis();

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        /**
         * join返回计算的结果或者抛出一个unchecked异常(CompletionException)，它和get对抛出的异常的处理有些细微的区别，你可以运行下面的代码进行比较：
         */
        // getOrJoin();

        // complete 使用
        // completeUse();

        // whenCompleteUse();
        // thenApplyUse();

        // thenAcceptBothUse();

        /**
         * 两个CompletionStage是并行执行的，它们之间并没有先后依赖顺序，other并不会等待先前的CompletableFuture执行完毕后再执行。
         * 其实从功能上来讲,它们的功能更类似thenAcceptBoth，只不过thenAcceptBoth是纯消费，它的函数参数没有返回值，而thenCombine的函数参数fn有返回值。
         */
        // thenCombineUse();

        /**
         * applyToEither方法是当任意一个CompletionStage完成的时候，fn会被执行，它的返回值会当作新的CompletableFuture<U>的计算结果。
         * 下面这个例子有时会输出100,有时候会输出200,哪个Future先完成就会根据它的结果计算。
         */
        // applyToEitherUse();

        /**
         * allOf方法是当所有的CompletableFuture都执行完后执行计算。 anyOf方法是当任意一个CompletableFuture执行完后就会执行计算，计算的结果相同。
         */

        allofOrAnyOfUse();

    }

    private static void allofOrAnyOfUse() throws InterruptedException, ExecutionException {
        Random rand = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        // CompletableFuture<Void> f = CompletableFuture.allOf(future1,future2);
        CompletableFuture<Object> f = CompletableFuture.anyOf(future1, future2);
        System.out.println(f.get());
    }

    private static void applyToEitherUse() throws InterruptedException, ExecutionException {
        Random rand = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
        System.out.println(f.get());
    }

    private static void thenCombineUse() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "abc";
        });
        CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);
        System.out.println(f.get()); // abc-100
    }

    private static void thenAcceptBothUse() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<Void> voidCompletableFuture =
            future.thenAcceptBoth(CompletableFuture.completedFuture(10), (x, y) -> {
                System.out.println("x=" + x);
                System.out.println("y=" + y);
                System.out.println(x * y);
            });
        // 这个地方输出null,因为返回值为void
        System.out.println(voidCompletableFuture.get());
    }

    private static void thenApplyUse() throws InterruptedException, ExecutionException {
        // 需要注意的是，这些转换并不是马上执行的，也不会阻塞，而是在前一个stage完成后继续执行。
        // 它们与handle方法的区别在于handle方法会处理正常计算值和异常，因此它可以屏蔽异常，避免异常继续抛出。而thenApply方法只是用来处理正常值，因此一旦有异常就会抛出。
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 100);
        CompletableFuture<String> f = future.thenApplyAsync(i -> i * 10).thenApplyAsync(i -> i.toString());
        System.out.println(f.get());
    }

    private static void whenCompleteUse() throws InterruptedException, ExecutionException, IOException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletableFutureStudy::getMoreData);
        CompletableFuture<Integer> f = future.whenComplete((v, e) -> {
            // 两个入参数
            System.out.println("v=" + v);
            System.out.println("e=" + e);
        });
        System.out.println(f.get());
        System.in.read();
    }

    private static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
        return random.nextInt(1000);
    }

    private static void completeUse() throws IOException {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;

            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                // 线程的名字和值
                try {
                    System.out.println(this.getName() + "：" + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("clinet1", f).start();
        new Client("clinet2", f).start();
        System.out.println("waiting");
        // future没有关联任何的Callback、线程池、异步任务等，如果客户端调用future.get就会一致傻等下去。你可以通过下面的代码完成一个计算，触发客户端的等待
        f.complete(100);
        System.in.read();
    }

    private static void getOrJoin() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // int i = 1 / 0;
            return 100;
        });
        //
        System.out.println("future.get()" + future.get());
        System.out.println("future.join()" + future.join());
    }

    /**
     * 代码中future没有关联任何的Callback、线程池、异步任务等，如果客户端调用future.get就会一致傻等下去。你可以通过下面的代码完成一个计算，触发客户端的等待
     * 
     * @return
     */
    public static CompletableFuture<Integer> compute() {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

}
