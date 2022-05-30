package com.study.queue.producerconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程操作口诀 1：操作一个公告的资源类 2：判断 干活 3：防止虚假唤醒
 * <p>
 * 一个初始值为0的参数被两个线程同时操作 一个进行加 一个进行减
 */
class LockShareData {

    private int number = 0;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    public void incr() throws InterruptedException {
        lock.lock();
        // 这里一定不能使用if判断 , 会存在线程的虚假唤醒的问题
        while (number != 0) {
            condition.await();
        }
        number ++;
        System.out.println(Thread.currentThread().getName() + " : " + number);
        // 通知所有线程
        condition.signalAll();
        lock.unlock();
    }

    public void decr() throws InterruptedException {
        lock.lock();
        while (number == 0) {
            condition.await();
        }
        number --;
        System.out.println(Thread.currentThread().getName() + " : " + number);
        // 通知所有线程
        condition.signalAll();
        lock.unlock();
    }

}

public class LockScheme {

    public static void main(String[] args) {
        LockShareData shareData = new LockShareData();

        // 这里 +1 和 减一 就相当于 生产者 消费者模式 ， 生产一个消费一个
        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } , "T1").start();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } , "T2").start();

    }

}
