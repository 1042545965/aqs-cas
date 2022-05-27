package com.study.queue.producerconsumer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个初始值为0的参数被两个线程同时操作 一个进行加 一个进行减
 */
class ShareData {
    private int number = 0;

    private Lock lock = new ReentrantLock();

    public void add(){

    }

}

public class OldScheme {


}
