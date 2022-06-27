package com.study.future;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskPollDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Long startTime = System.currentTimeMillis();

        // m1();
        FutureTask<Object> futureTask1 = new FutureTask<>(
            () -> {
                TimeUnit.SECONDS.sleep(5L);
                System.out.println("hello futureTask1 ");
                return "futureTask1 over";
            }
        );
        executorService.execute(futureTask1);

        FutureTask<Object> futureTask2 = new FutureTask<>(
            () -> {
                TimeUnit.SECONDS.sleep(4L);
                System.out.println("hello futureTask2 ");
                return "futureTask2 over";
            }
        );
        executorService.execute(futureTask2);

        FutureTask<Object> futureTask3 = new FutureTask<>(
            () -> {
                TimeUnit.SECONDS.sleep(3L);
                System.out.println("hello futureTask3 ");
                return "futureTask3 over";
            }
        );
        executorService.execute(futureTask3);
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());
        // 抛出异常 ， 避免阻塞 不够优雅
//        System.out.println(futureTask3.get(2 , TimeUnit.SECONDS));
//        // 通过死循环 ， 空转CPU 消耗性能 但是不会被阻塞了
//        while (true){
//            if (futureTask1.isDone()){
//                System.out.println(futureTask1.get());
//                break;
//            }else {
//                System.out.println("在处理......");
//            }
//        }
//        Long endTime = System.currentTimeMillis();
//        executorService.shutdown();
//        System.out.println("main 耗时 ： " + (endTime - startTime));
    }


    public static void m1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5L);
        TimeUnit.SECONDS.sleep(4L);
        TimeUnit.SECONDS.sleep(3L);
    }

}
