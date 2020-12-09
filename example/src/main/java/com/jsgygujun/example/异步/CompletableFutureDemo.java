package com.jsgygujun.example.异步;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author GuJun
 * @date 2020/12/9
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {

    }

    // 创建一个完成的 CompletableFuture
    @Test
    public void completedFutureExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.isDone()); // true
        System.out.println(cf.getNow(null)); // message
        // getNow(null)方法在future完成的情况下会返回结果，就比如上面这个例子，否则返回null (传入的参数)。
    }

    // 运行一个简单的异步阶段
    // 说明：
    // CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现，
    // 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为。
    @Test
    public void runAsyncExample() {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-9
            System.out.println("isDaemon： " + Thread.currentThread().isDaemon()); // isDaemon： true
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(cf.isDone()); // false
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cf.isDone()); // true
    }

    // 在前一个阶段上应用函数
    // 说明：
    // 下面这个例子使用前面 #1 的完成的CompletableFuture， #1返回结果为字符串message,然后应用一个函数把它变成大写字母。
    // 注意thenApply方法名称代表的行为。
    // then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
    // Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
    // 函数的执行会被阻塞，这意味着getNow()只有等操作被完成后才返回。
    @Test
    public void thenApplyExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApply(String::toUpperCase);
        System.out.println(cf.getNow(null)); // MESSAGE
    }

    // 在前一阶段上异步应用函数
    @Test
    public void thenApplyAsyncExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync( s -> {
            System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-9
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        });
        System.out.println(cf.getNow(null)); // null
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cf.getNow(null)); // MESSAGE
    }

    @Test
    public void test1() {
        CompletableFuture<String> cf = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-9
                    return "message";
                })
                .thenApplyAsync(s -> {
                    System.out.println(Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-9
                    return s.toUpperCase();
                });
        System.out.println(cf.join()); // MESSAGE
    }
}
