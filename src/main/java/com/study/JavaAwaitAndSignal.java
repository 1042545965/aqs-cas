package com.study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证可重入锁代码
 */
class JavaAwaitAndSignal {


    public static Lock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        // 这里 要突出的是 没有 lock.lock() 和  lock.unlock() 就会报错 运行 不能 先 signal 在 await
        new Thread(() -> {
//            TimeUnit.SECONDS.sleep(3L);
            lock.lock();
            try {
                System.out.println("来啦=大爷");
                condition.await();
                System.out.println("唤醒=大爷");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            condition.signal();
            System.out.println("通知=大爷");
            lock.unlock();
        }, "t2").start();
    }

}
