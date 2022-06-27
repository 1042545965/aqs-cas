package com.study.future.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 实际需求
 * 1. 同一个产品在各大电商平台的售价
 * 2. 同一个产品在相同电商平台的不同商户的售价
 */

public class CompletableFutureDemandDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                //使用sleep()模拟耗时操作
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 2);
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(future1, future1);
//        System.out.println(voidCompletableFuture.get());
        // 输出3
        System.out.println(future2.join()+future1.join());




    }


}
