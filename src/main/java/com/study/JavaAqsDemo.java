package com.study;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1: AQS 就是一个状态 + 队列
 * 2: 状态 0 就是没人占用
 * 3: lock.lock() compareAndSetState(0,1) 比较并设置 CAS 底层调用的是 unsafe  这个时候 A 进来了 那么状态被设置为 1了
 * 4: lock.lock() setExclusiveOwnerThread (独家所有者) 表明 A 已经占用到了这个锁
 * 5: lock.lock() acquire(1) 这个时候 B 拿不到锁了 只能走 这个方法
 * 6：lock.lock() acquire TryAcquire(nonfairTryAcquire) 状态是 1  当前线程不是exclusive 当中的线程只能返回 false 这当中的两个判断
 *      a：if (c == 0) 如果 刚刚好 A 完成了 那么 B就持有锁了
 *      b: current == getExclusiveOwnerThread() 如果这个线程就是 A 自己 状态 + 1 就是重入锁的体现了
 * 7: lock.lock() acquire acquireQueued addWaiter 在这里 B 没有获取锁 那 B 线程正式进入队列 这里 就 表明了将线程封装成 node 节点
 *      a: enq() 自旋 哨兵节点 用来占位 头尾节点 在一次进入 B 节点就进入中间节点 这个方法很重要 ！！！
 * 8: lock.lock() acquire acquireQueued 这里面又是自旋 node.predecessor(); B 的角度 获取的就是 哨兵节点
 *      a: shouldParkAfterFailedAcquire(p, node) 将哨兵节点的值设置为 -1 自旋的情况下 第二次会 return true
 *      b: parkAndCheckInterrupt() 使用 LockSupport.pack 这个时候 B 被阻塞了  执行到这里就不需要在往下走了
 * 9: 到最后开始 lock.unlock 使用的 sync.release(1);
 *      a: tryRelease() int c = getState() - releases 将状态设置为 0
 *      b: setExclusiveOwnerThread(null); 将A的状态设置为null
 *      c: unparkSuccessor(h) LockSupport.unpark(s.thread); b 节点不为 null 这里b节点的 park 就会被解锁
 * 总结流程:
 *      加锁流程 : 比较并交换设置状态 将状态设置为 1 , exclusive Owner 放入当前线程 -> 比对完状态和线程后进入队列 -> 在acquireQueued中 使用 lockSupport.pack 阻塞
 *      解锁流程 : lock.unlock -> 将状态值设置为0 , 将 exclusive Owner 设置为空 -> unparkSuccessor 进行unpack解锁
 *      闭环流程 : 由于 在acquireQueued中 使用的是自旋 所以 tryAcquire(arg) B就会走比较并交换 进入加锁流程 ,
 *          设置队列 : 在设置 队列 head 节点 由哨兵转为B , B节点的前指针设置为null ,这个时候哨兵节点就没有引用 会被GC return 后自旋结束
 *
 *  几个重要的 需要记住的方法  compareAndSetState 比较设置状态 setExclusiveOwnerThread 设置node线程 tryAcquire：再次抢占锁  addWaiter 将封装好的线程放到队列 acquireQueued 处理队列当中的节点
 */
class JavaAqsDemo {


    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("A Thread come in");
                TimeUnit.SECONDS.sleep(20L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } , "A").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("B Thread come in");
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } , "B").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("C Thread come in");
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } , "C").start();
    }



}
