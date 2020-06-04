package com.wefree.demo.lockutils.reentrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReentrantLockClient {
    public static void main(String[] args) {
        ConditionBuffer<String> que = new ConditionBuffer<>(10);

        int cupCount = Runtime.getRuntime().availableProcessors();
        ExecutorService mExecutor = Executors.newFixedThreadPool(2*cupCount);
        mExecutor.submit(()->{
            try {
                while (true) {
                    Thread.sleep(5000);
                    String product = que.take();
                    System.out.println("消费：" + product);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 随机抢占读入锁
        for(int i=0;i<9;i++){
            mExecutor.submit(()->{
                try {
                    while (true) {
                        Thread.sleep(1000);
                        long product = (long) (Math.random() * 100);
                        que.put(String.valueOf(product));
                        System.out.println("生产：" + product);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
