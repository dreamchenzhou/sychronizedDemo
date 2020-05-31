package com.wefree.demo.interrrupt.witeresume;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 因式分解
 */
public class PrimeProducerWithResume extends Thread {

    private BlockingQueue<BigInteger> queue;

    public PrimeProducerWithResume(BlockingQueue<BigInteger> queue){
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
                System.out.println("生产："+bigInteger.intValue());
            }
        }catch (InterruptedException e){
            System.out.println("生成者被中断："+e.getMessage());
            boolean isInterrupted =isInterrupted();
            // 使整个线程处于中断状态，从而阻塞的任务执行不了，下面代码等同直接调用 interrupt();
            Thread.currentThread().interrupt();
            System.out.println("中断恢复");
        }
        try {
            boolean isInterrupted =isInterrupted();
            interrupted(); //该静态代码使得线程恢复非中断状态，阻塞任务又可以执行了
            System.out.println("清理产品："+queue.take());
            System.out.println("生成者清理工作执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
