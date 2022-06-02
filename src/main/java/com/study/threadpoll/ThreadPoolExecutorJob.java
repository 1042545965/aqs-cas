package com.study.threadpoll;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 工作当中合理的配置线程池
 *      分业务
 *          CPU密集型
 *              1：
 *          IO 密集型
 */
public class ThreadPoolExecutorJob {

    public static void main(String[] args) {

        jobThreadPool();

    }


    /**
     * 工作当中使用线程池
     */
    private static void jobThreadPool() {
        // 核心线程数 超过核心线程数就不会在去创建了 如果有任务，那么就会去执任务
        // 当线程池中的线程数量到了  corePoolSize 后就会放入队列
        int corePoolSize = 2;
        // 最大线程数  我最多能开的服务窗口 就是 当  workQueue 满了 corePoolSize 没用空闲了 就会新开线程
        int maximumPoolSize = 5;
        // 存活时间  空闲下来的非核心线程 存活的时间
        long keepAliveTime = 1;
        // 存活时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 阻塞队列 任务会在这里等待  类似于候客区
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(3);
        // 线程工厂 一个一个的线程我怎么去创建
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        /**
         * 拒绝策略
         * AbortPolicy 默认的 , 生产不能用 ，怎么能抛异常出去呢
         * CallerRunsPolicy 回退回调用的线程 . main 线程去办理这个业务了
         * DiscardOldestPolicy 抛弃等待时间最长的
         * DiscardPolicy  直接丢弃任务
         * */
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, unit, blockingQueue, threadFactory, handler
        );

        try {
            // 是个用户的请求 都是用  fixedThreadPool 线程池来处理
            for (int i = 0; i < 10; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " : 办理业务");
                });
            }
        } finally {
            threadPoolExecutor.shutdown();
        }

        // 重点流程记录 !!
        // 在 扩容的时候 在来任务 ， 那么不会先走队列里面的任务 。新来的任务会插队
    }

}
