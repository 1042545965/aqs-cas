package com.study.cas;

/**
 * Volatile 不保证原则性
 */
class JavaVolatileNotAtom {

    static class MyData {

        private volatile int number = 0;

        /**
         * synchronized 添加之后就能保证原子性 number++ 实际上并不是一个操作 ， 在底层被分解为了多个操作了
         *        2: getfield      #2                  // Field number:I
         *        5: iconst_1
         *        6: iadd
         *        7: putfield      #2                  // Field number:I
         * 这里是javap 查看的字节码文件
         * 1: getfield 从物理内存(共享内存)获取到值
         * 2: iadd 进行+1 这里是在工作内存(栈内存)+1
         * 3: putfield 将计算完的值 放入共享内存
         */
        public synchronized void addPushPush() {
            this.number++;
        }
    }

    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPushPush();
                }
            }, String.valueOf(i)).start();
        }
        // Thread.activeCount() 在main 方法当中运行一定会有两个 线程 main 线程 和 GC 线程
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        // 按理来说是 20 * 1000 = 20000 但是如果不添加 synchronized 就不会是 20000
        System.out.println(Thread.currentThread().getName() + " : " + myData.number);

    }
}
