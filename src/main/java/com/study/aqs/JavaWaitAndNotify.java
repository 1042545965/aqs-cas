package com.study.aqs;

/**
 * 验证可重入锁代码
 */
class JavaWaitAndNotify {



    public static final Object lockObj = new Object();

    public static void main(String[] args) {
        // 这里 要突出的是 wait notify 不能脱离 synchronized 运行 不能 先 notify 在 wait
        new Thread(()-> {
//            TimeUnit.SECONDS.sleep(3L);
            synchronized (lockObj){
                System.out.println("来啦=大爷");
                try {
                    lockObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("大爷=唤醒");
            }
        } , "t1").start();

        new Thread(()-> {
            synchronized (lockObj){
                lockObj.notify();
                System.out.println("通知=大爷");
            }
        } , "t2").start();
    }

}
