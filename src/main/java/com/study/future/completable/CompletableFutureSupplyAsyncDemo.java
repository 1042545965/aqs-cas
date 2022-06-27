package com.study.future.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureSupplyAsyncDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> helloWord = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("hello word");
            return "hello supplyAsync";
        });

        System.out.println(helloWord.get());
    }

}
