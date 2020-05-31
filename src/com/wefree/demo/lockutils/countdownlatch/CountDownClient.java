package com.wefree.demo.lockutils.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownClient {

    // 模拟十人赛跑
    public static void main(String[] args) throws InterruptedException {
        int runnerCounts = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch beginCountDown = new CountDownLatch(1);
        CountDownLatch endCountDown = new CountDownLatch(runnerCounts);
        for(int i=0;i<runnerCounts;i++){
            final int no = i+1;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            beginCountDown.await();
                            long time = (long) (Math.random()*10000);
                            Thread.sleep(time);
                            System.out.println("NO."+no+"跑完.用时："+time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            endCountDown.countDown();
                        }
                    }
                };
                executorService.submit(runnable);
        }
        System.out.println("预备！跑！");
        beginCountDown.countDown();
        endCountDown.await();
        System.out.println("赛跑结束");
        executorService.shutdownNow();
    }


}
