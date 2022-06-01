package com.study.queue.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程操作口诀 1：操作一个公告的资源类 2：判断 干活 3：防止虚假唤醒
 * <p>
 * 一个初始值为0的参数被两个线程同时操作 一个进行加 一个进行减
 */
class QueueShareData {

    private volatile boolean flag = true;

    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue = null;

    public QueueShareData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        boolean offer;
        String data;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            offer = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (offer){
                System.out.println("插入队列 " + data + "成功");
            }else {
                System.out.println("插入队列 " + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("生产停止");
    }


    public void myConsumer() throws InterruptedException {
        while (flag) {
            String poll = blockingQueue.poll(2, TimeUnit.SECONDS);
            System.out.println("消费成功 ： " + poll);
            if (null == poll || poll.equalsIgnoreCase("")){
                flag = false;
                System.out.println("消费不到 退出");
                // 一定要加 return 不然死循环了
                return;
            }
        }
    }

    public void stop(){
        this.flag = false;
    }
}

public class QueueScheme {

    public static void main(String[] args) {
        QueueShareData queueShareData = new QueueShareData(new ArrayBlockingQueue<>(10));

        new Thread(()-> {
            try {
                System.out.println("生产线程开始");
                queueShareData.myProd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } , "T1").start();

        new Thread(()-> {
            try {
                System.out.println("消费线程开始");
                queueShareData.myConsumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } , "T2").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        queueShareData.stop();
        System.out.println("老板发话 活动结束");
    }

}
