package com.wefree.demo.lockutils.semaphore;

public class SemaphoreClient {
    public static void main(String[] args) {

        System.out.println("current cup thread:"+Runtime.getRuntime().availableProcessors());
        BoundHashSet<String> boundHashSet = new BoundHashSet<>(10);
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep((long) (Math.random()*1 * 1000));
                    String item = "item" + Math.random() * 10000;
                    boolean isAdded = boundHashSet.add(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep((long) (Math.random()*1.5*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boundHashSet.removeNext();
            }
        }).start();

        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("current size="+boundHashSet.size());
            }
        }).start();

    }
}
