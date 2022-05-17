package com.study;

/**
 * 验证可重入锁代码
 */
class JavaSynchronizedLock {



    public static final Object lockObj = new Object();

    public static void m(){
        synchronized (lockObj){
            System.out.println("进入外层");
            synchronized (lockObj){
                System.out.println("进入中层");
                synchronized (lockObj){
                    System.out.println("进入内层");
                }
            }
        }
    }


    public static void m2(){
        synchronized (lockObj){
            System.out.println("进入外层");
            synchronized (lockObj){
                System.out.println("进入中层");
                synchronized (lockObj){
                    System.out.println("进入内层");
                }
            }
        }
    }

    public static void main(String[] args) {
        m();
    }

}
