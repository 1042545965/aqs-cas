package com.study.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AbaDemo {
    // 以下是 ABA 问题的产生
    private static final AtomicReference<Integer> atomicReferenceIssue = new AtomicReference<>(100);
    // initialStamp 版本号
    private static final AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100 , 1);
    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            atomicReferenceIssue.compareAndSet(100, 101);
            atomicReferenceIssue.compareAndSet(101, 100);
        }, "T1").start();

        TimeUnit.SECONDS.sleep(3);
        new Thread(() -> {
            boolean compareAndSet = atomicReferenceIssue.compareAndSet(100, 2019);
            System.out.println(compareAndSet + " : " + atomicReferenceIssue.get());
        }, "T2").start();

        // 以下是 ABA 问题的解决
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " : 第一次版本号 ： " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(100, 101 , atomicStampedReference.getStamp() , atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + " : 第二次版本号 ： " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100 , atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
        }, "T3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " : 第一次版本号 ： " + stamp);
            try {
                // 为了第四次 版本号不同
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean compareAndSet = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
            System.out.print(Thread.currentThread().getName() + " : 第二次版本号 ： " + stamp + " : ");
            System.out.println(compareAndSet + " : " + atomicStampedReference.getStamp() + " : value :" + atomicStampedReference.getReference());
        }, "T4").start();
    }
}
