package com.wefree.demo.lockutils.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierClient {
    public static void main(String[] args) {
        int threadCounts = Runtime.getRuntime().availableProcessors() * 2;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCounts);
        for (int i = 0; i < threadCounts; i++) {
            final int no = i;
            new Thread(() -> {
                long time = (long) (Math.random() * 10000);
                try {
                    Thread.sleep(time);
                    System.out.println("NO." + no + "执行完毕,等待其他人执行完毕");
                    cyclicBarrier.await();
                    System.out.println("NO." + no + "等待完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(3000);
                    //如果调用下面一行代码，cyclicBarrier.await()将不会等待，并抛出异常
                    //cyclicBarrier.reset();
                    int wait = cyclicBarrier.getNumberWaiting();
                    int parities = cyclicBarrier.getParties();
                    System.out.println(String.format("parties=%s,wait=%s", parities, wait));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
