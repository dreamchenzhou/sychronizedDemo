package com.wefree.demo.interrrupt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 因式分解
 */
public class PrimeProducer extends Thread {

    private BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    public void cancel(){
        interrupt();
    }
    @Override
    public void run() {
        try{
            BigInteger bigInteger =BigInteger.ONE;
            while(!Thread.currentThread().isInterrupted()){
                queue.put(bigInteger=bigInteger.nextProbablePrime());
            }
        }catch (InterruptedException e){
            System.out.println("生成者被中断："+e.getMessage());
            //后面的阻塞代码块都不能正常运行
            Thread.currentThread().interrupt();
        }
    }
}
