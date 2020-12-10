package com.jsgygujun.example.异步;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author GuJun
 * @date 2020/12/9
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {

    }

    // 1. 新建一个完成的 CompletableFuture
    // 通常作为计算的起点
    @Test
    public void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.isDone()); // true
        System.out.println(cf.getNow(null)); // message
        // getNow(null)方法在future完成的情况下会返回结果，就比如上面这个例子，否则返回null (传入的参数)。
    }

    // 2. 运行一个简单的异步阶段
    // 说明：
    // CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)， 异步执行通过ForkJoinPool实现，
    // 它使用守护线程去执行任务。注意这是CompletableFuture的特性， 其它CompletionStage可以override这个默认的行为。
    @Test
    public void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture
                .runAsync(() -> {
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

    // 3. 将方法作用域前一个 Stage
    // 说明：
    // 下面这个例子使用前面 #1 的完成的CompletableFuture， #1返回结果为字符串message,然后应用一个函数把它变成大写字母。
    // 注意thenApply方法名称代表的行为。
    // then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
    // Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
    // 函数的执行会被阻塞，这意味着getNow()只有等操作被完成后才返回。
    @Test
    public void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture
                .completedFuture("message")
                .thenApply(String::toUpperCase);
        System.out.println(cf.getNow(null)); // MESSAGE
    }

    // 4. 异步的将方法作用于前一个 Stage
    @Test
    public void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.
                completedFuture("message")
                .thenApplyAsync( s -> {
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

    // 5. 使用自定义的 Executor 来异步执行该方法
    final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    @Test
    public void thenApplyAsyncWithExecutorExample() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(s -> {
                    System.out.println(Thread.currentThread().getName());
                    return s.toUpperCase();
                }, executorService);
        cf.join();
        System.out.println(cf.get());
    }

    // 6. 消费前一个 Stage 结果
    @Test
    public void thenAcceptExample() {
        CompletableFuture<Void> cf = CompletableFuture
                .completedFuture("message")
                .thenAccept(s -> {
                    System.out.println(s + "HHHH");
                });
    }

    // 7. 异步消费前一个 Stage 结果
    @Test
    public void thenAcceptAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture
                .completedFuture("message")
                .thenAcceptAsync(s -> {
                    System.out.println(s + "HHHH");
                });
        cf.join();
    }

    // 8. 计算出现异常
    @Test
    public void completeExceptionallyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("1message").thenApplyAsync(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        });
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> {
            return (th != null) ? "message upon cancel" : "";
        }); // 创建了一个 handler stage，exceptionHandler，这个阶段会处理一切异常并返回另一个消息 message upon cancel。
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        try {
            cf.join();
        } catch(Exception ex) {
            System.out.println(ex.getCause().getMessage()); // completed exceptionally
        }
        exceptionHandler.join();
    }

    // 9. 取消计算
    @Test
    public void cancelExample() {

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
