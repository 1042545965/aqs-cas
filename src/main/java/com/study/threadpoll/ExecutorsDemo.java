package com.study.threadpoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo {

    public static void main(String[] args) {
        // 固定线程
        jobTreadPool(Executors.newFixedThreadPool(5) , "newFixedThreadPool");
        // 单个线程
        jobTreadPool(Executors.newSingleThreadExecutor() , "newSingleThreadExecutor");
        // 缓存 随机扩容 线程
        jobTreadPool(Executors.newCachedThreadPool() , "newCachedThreadPool");
    }

    private static void jobTreadPool(ExecutorService executorService , String method) {
        try {
            // 是个用户的请求 都是用  fixedThreadPool 线程池来处理
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +" : 办理业务 ：" + method);
                });
            }
        } finally {
            executorService.shutdown();
        }
    }

}
