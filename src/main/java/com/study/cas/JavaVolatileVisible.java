package com.study.cas;

import java.util.concurrent.TimeUnit;

class JavaVolatileVisible {

    static class MyData {

        /**
         * 如果不添加 volatile 那么 main 线程不会被同步 , 因为main不会被同步
         * */
        private volatile int number = 0;

        public void addNumber() {
            this.number = 60;
        }
    }


    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println("A come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 将 0 改为 60
            myData.addNumber();
            System.out.println("number is " + myData.number);
        }, "A").start();

        while (myData.number == 0){

        }

        System.out.println("over");
    }
}
