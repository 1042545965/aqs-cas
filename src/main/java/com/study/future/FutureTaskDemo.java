package com.study.future;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Object> futureTask = new FutureTask<>(new MyCallableThread());
        Thread t1 = new Thread(futureTask, "T1");
        t1.start();
        System.out.println(futureTask.get());
    }

}

class MyCallableThread implements Callable<Object> {

    @Override
    public Object call() throws Exception {
        System.out.println("hello Callable call........");
        return "hello Callable";
    }
}

class MyRunnableThread implements Runnable {

    @Override
    public void run() {

    }
}

class MyRunnableFutureThread implements RunnableFuture<Object> {


    @Override
    public void run() {

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}

class MyFutureTaskThread extends FutureTask<Object> {

    public MyFutureTaskThread(Callable<Object> callable) {
        super(callable);
    }
}