package com.study.queue.block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueBooleanDemo {

    public static void main(String[] args) {
        // 这里和 arrayList 不同的是需要指定 边界 ，arrayList 默认是10
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // 添加
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        // 在添加一组 会抛出异常
         System.out.println(blockingQueue.offer("d"));
        // 检查 队列顶端元素
        System.out.println(blockingQueue.peek());
        // 取数据
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

    }

}
