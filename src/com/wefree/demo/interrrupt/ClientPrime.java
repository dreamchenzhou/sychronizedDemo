package com.wefree.demo.interrrupt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientPrime {
    private static List<BigInteger> primeList = new ArrayList<>();
    private static int source=1345657560;

    public static void main(String[] args){
        consumerPrimes(source);
    }

    private static void consumerPrimes(final int value){
        BlockingQueue<BigInteger> bigIntegers = new ArrayBlockingQueue<BigInteger>(1);
        PrimeProducer primeProducer = new PrimeProducer(bigIntegers);
        primeProducer.start();
        try {
            while(needMorePrimes()) {
                BigInteger prime = bigIntegers.take();
                if (value % prime.intValue() == 0) {
                    consume(prime);
                }
            }
            primeProducer.cancel();
            StringBuilder builder = new StringBuilder();
            for (BigInteger i:primeList){
                builder.append(i);
                builder.append(",");
            }
            System.out.println("因式分解结果："+builder.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void consume(BigInteger prime) throws InterruptedException {
        while(source%prime.intValue()==0){
            primeList.add(prime);
            source = source/prime.intValue();
            //消费者时间
            Thread.sleep(1000);
        }
    }
    private static boolean needMorePrimes(){
        return 1!=source;
    }
}
