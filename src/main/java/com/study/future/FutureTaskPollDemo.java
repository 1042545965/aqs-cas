package com.study.future;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskPollDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Long startTime = System.currentTimeMillis();

        // m1();
        FutureTask<Object> futureTask1 = new FutureTask<>(
            ()-> {TimeUnit.SECONDS.sleep(5L);System.out.println("hello futureTask1 over");return "futureTask1 over";}
        );
        executorService.execute(futureTask1);
        System.out.println(futureTask1.get());
        FutureTask<Object> futureTask2 = new FutureTask<>(
            ()-> {TimeUnit.SECONDS.sleep(4L);
                System.out.println("hello futureTask2 over");return "futureTask2 over";}
        );
        executorService.execute(futureTask2);
        System.out.println(futureTask2.get());
        FutureTask<Object> futureTask3 = new FutureTask<>(
            ()-> {TimeUnit.SECONDS.sleep(3L);System.out.println("hello futureTask3 over");return "futureTask3 over";}
        );
        executorService.execute(futureTask3);
        System.out.println(futureTask3.get());
        Long endTime = System.currentTimeMillis();
        executorService.shutdown();
        System.out.println("耗时 ： " + (endTime - startTime));
    }


    public static void m1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5L);
        TimeUnit.SECONDS.sleep(4L);
        TimeUnit.SECONDS.sleep(3L);
    }

}
