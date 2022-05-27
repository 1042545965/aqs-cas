package com.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁 : 循环比较直到成功为止
 */
public class SpinLockDemo {
    static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mySpinLock(){
        // 返回的是当前正在执行的线程
        Thread thread = Thread.currentThread();
        // new AtomicReference<>() 初始值就是null expect 就是null ,所以第一次比较会把 thread 放进去
        while (!atomicReference.compareAndSet(null , thread)){
            // 因为取反 , 所以第一次就不会进这个while循环

        };
    }

    public void myUnSpinLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread , null);
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()-> {
            spinLockDemo.mySpinLock();
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            try {
                // 保证T2 已经执行 但是 T1 还没执行完毕
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕");
            spinLockDemo.myUnSpinLock();
        } , "T1").start();
        // 保证 T1 先使用到锁
        TimeUnit.SECONDS.sleep(1);
        new Thread(()-> {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            spinLockDemo.mySpinLock();
            System.out.println(Thread.currentThread().getName() + " 执行完毕");
            spinLockDemo.myUnSpinLock();
        } , "T2").start();
    }

}
