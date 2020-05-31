package com.wefree.demo.interrrupt.witeresume;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientInterruptResume {

    public static void main(String[] args) {
        BlockingQueue<BigInteger> bigIntegers =new ArrayBlockingQueue<>(3);
        PrimeProducerWithResume primeProducer =new PrimeProducerWithResume(bigIntegers);
        primeProducer.start();
        try {
            // 生产一定的产品后，关闭生产服务，完成生成后期清理工作
            Thread.sleep(50);
            primeProducer.cancel();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
