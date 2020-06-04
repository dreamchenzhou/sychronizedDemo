package com.wefree.demo.lockutils.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBuffer<T> {
    protected final Lock  lock = new ReentrantLock();
    protected final Condition notFull = lock.newCondition();
    protected final Condition notEmpty = lock.newCondition();
    private int head,tail,bufferSize,count;


    private T [] items= null;

    public ConditionBuffer(int bufferSize){
        this.bufferSize=bufferSize;
        items = (T[]) new Object[bufferSize];
    }


    public void put(T t)  {
        lock.lock();
        try {
            while (count==items.length){
                notFull.await();
                System.out.println(Thread.currentThread().getName()+ "获取到写入锁");
            }
            items[tail]=t;
            if(++tail==items.length){
                tail=0;
            }
            ++count;
            notEmpty.signal();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }


    public T take() throws InterruptedException {
        T t=null;
        lock.lock();
        try{
            while (count==0){
                notEmpty.wait();
                System.out.println(Thread.currentThread().getName()+ "获取到读出锁");
            }
            t = items[head];
            items[head]=null;
            if(++head==items.length){
                head=0;
            }
            --count;
            notFull.signal();
            return t;
        }finally {
            lock.unlock();
        }

    }
}
