package com.study;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证可重入锁代码
 */
class JavaReentrantLock {



    public static final Lock lock = new ReentrantLock();

    public static void m(){
        // 这里有一个点需要注意 加几次锁就需要 解锁几次 ，不然的话 别的线程无法等到你释放锁 (这里和 synchronized 有不同因为是显示的 只有  lock.unlock() 才会减一)
        lock.lock();
        System.out.println("进入上层");
        try {
            lock.lock();
            System.out.println("进入中层");
            try {
                lock.lock();
                System.out.println("进入内层");
            }finally {
                lock.unlock();
            }
        }finally {
            lock.unlock();
        }
        lock.unlock();
    }


    public static void main(String[] args) {
        new Thread(JavaReentrantLock::m, "t1").start();;
        new Thread(()-> {
            lock.lock();
            System.out.println("t2 启动");
            lock.unlock();
        }, "t2").start();;
    }

}
