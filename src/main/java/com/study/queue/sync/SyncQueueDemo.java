package com.study.queue.sync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SyncQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        // 这里和 arrayList 不同的是需要指定 边界 ，arrayList 默认是10
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        // 以下这段代码 说明同步队列一定要等到后一个take完才会取put下一个
        new Thread(()-> {
            try {
                System.out.println(Thread.currentThread().getName() +" PUT 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() +" PUT 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() +" PUT 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } , "T1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " GET " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " GET " + blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " GET " + blockingQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } , "T2").start();


    }

}
