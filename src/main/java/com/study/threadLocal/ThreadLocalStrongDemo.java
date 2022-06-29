package com.study.threadLocal;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

class GcTest {
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("my_finalize");
    }
}

public class ThreadLocalStrongDemo {

    public static void main(String[] args) throws InterruptedException {
        GcTest gcTest = new GcTest();
        System.out.println("gc...before");
        gcTest = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("gc...after");
    }
}

/**
 * 软引用 ， 内存够 不会被干掉
 * 弱引用 ， 只要虚拟机GC就会被干掉
 * 虚引用 ， 只能和引用队列存在 ， 所以不能单独使用 , get 方法总是返回null ，只能是引用队列里面存在 ， 才能来做点什么事情
 */
class ThreadLocalSoftDemo {

    public static void main(String[] args) throws InterruptedException {
        // 需要设置 jvm参数才能出现这个情况  -Xms10m -Xmx10m
        SoftReference<GcTest> softReference = new SoftReference<>(new GcTest());
        System.gc();
        System.out.println("gc...before....." + softReference.get());
        try {
            byte[] bytes = new byte[20 * 1024 * 1024];
        }catch (Exception t){
            t.printStackTrace();
        }finally {
            System.out.println("gc...after...."+ softReference.get());
        }
    }
}

class ThreadLocalWhyReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()-> 1);
        // main 方法new 出来的是强引用 ， 如果 Entry 也是强引用 那么线程结束 ， 出栈了 ，这个时候线程没了 ， 但是 Entry 有强引用 。那么就不会被GC
        // Entry 当中的key 是null ， value 还继续存在 ，如果经常存在线程服用 ，value 是由null作为key指定的 ， 那么这个 map中就有很多的 key是null 的Entry 。就会内存泄漏
    }
}