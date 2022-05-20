package com.study.cas;

/**
 * Volatile 不保证原则性
 */
class JavaVolatileSingleton {

    /**
     * 使用 volatile 禁止指令重排
     */
    private static volatile JavaVolatileSingleton singleton = null;

    public JavaVolatileSingleton() {
        System.out.println("ThreadName : " + Thread.currentThread().getName() + " my coming");
    }

    /**
     * 最初不添加 synchronized my coming  会被 打野多次 添加了之后就可以了 。但是在高并发模式下性能不太好
     */
    public synchronized static JavaVolatileSingleton getSingletonSynchronized() {
        if (singleton == null) {
            singleton = new JavaVolatileSingleton();
        }
        return singleton;
    }

    /**
     * DCL 双端检索机制 不一定能保证单例 ， 但是因为存在之类重排 . 所以需要添加 volatile 禁止指令重排
     */
    public static JavaVolatileSingleton getSingletonDcl() {
        if (singleton == null) {
            synchronized (JavaVolatileSingleton.class) {
                if (singleton == null) {
                    singleton = new JavaVolatileSingleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                JavaVolatileSingleton.getSingletonSynchronized();
            }, String.valueOf(i)).start();
        }
    }
}
