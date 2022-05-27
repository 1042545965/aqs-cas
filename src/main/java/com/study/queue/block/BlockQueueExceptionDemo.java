package com.study.queue.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueExceptionDemo {

    public static void main(String[] args) {
        // 这里和 arrayList 不同的是需要指定 边界 ，arrayList 默认是10
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // 添加
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // 在添加一组 会抛出异常
         System.out.println(blockingQueue.add("d"));
        // 检查
        System.out.println(blockingQueue.element());
        // 移除
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

    }

}
