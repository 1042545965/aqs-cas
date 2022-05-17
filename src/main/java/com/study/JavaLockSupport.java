package com.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 验证可重入锁代码
 */
class JavaLockSupport {



    public static final Object lockObj = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {TimeUnit.SECONDS.sleep(3L);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("来啦=大爷");
            LockSupport.park();
            System.out.println("唤醒=大爷");
        }, "t1");
        t1.start();

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println("通知=大爷");
        }, "t2").start();
    }

}
