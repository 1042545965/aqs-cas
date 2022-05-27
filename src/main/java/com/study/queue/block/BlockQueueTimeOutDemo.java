package com.study.queue.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockQueueTimeOutDemo {

    public static void main(String[] args) throws InterruptedException {
        // 这里和 arrayList 不同的是需要指定 边界 ，arrayList 默认是10
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // 添加  这里是阻塞 消息中间件底层需要用到 ， 不能丢消息的
        System.out.println(blockingQueue.offer("a" , 2 , TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println("****************************");
        // blockingQueue.put("d");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        // 到第四个 没有就一直等着消费
        System.out.println(blockingQueue.take());


    }

}
