package com.study.deadlock;

class HoldLockThread implements Runnable {

    private final String lockA;
    private final String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "已经持有 " + lockA + " 需要去获取 " + lockB);
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "已经持有 " + lockB + " 需要去获取 " + lockA);
            }
        }
    }
}


public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "T1").start();
        new Thread(new HoldLockThread(lockB, lockA), "T2").start();
    }

}
