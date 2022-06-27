package com.study.future.completable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWhenApiDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            futrue1(executorService);
            System.out.println(Thread.currentThread().getName() + " 去做其他任务了");
        }finally {
            executorService.shutdown();
        }
    }

    public static void futrue1(ExecutorService executorService) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> helloWord = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("hello word");
            return ThreadLocalRandom.current().nextInt();
        } , executorService).whenComplete((v , e)->{
            if (e == null){
                System.out.println("update hello word " + v);
            }
        }).exceptionally(e-> {
            System.out.println("这个异常是 ： " + Arrays.toString(e.getStackTrace()));
            return null;
        });
    }

}
