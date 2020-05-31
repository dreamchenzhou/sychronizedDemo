package com.wefree.demo.future.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureClient {
    public static void main(String[] args) {

        DownloadFutureTask futureTask = new DownloadFutureTask(()-> {
                String url = "http://www.baidu.com";
                Thread.sleep(3*1000);
                return url;
        });

        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            // 下面代码可以中断任务中断阻塞任务
           // thread.interrupt();
            String result = futureTask.get(4 * 1000, TimeUnit.MILLISECONDS);
            boolean isDone = futureTask.isDone();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
