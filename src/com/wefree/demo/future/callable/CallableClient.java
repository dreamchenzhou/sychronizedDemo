package com.wefree.demo.future.callable;

import java.util.concurrent.*;

public class CallableClient {



    public static void main(String[] args) {
        DownloadCallable callTask = new DownloadCallable();
        ExecutorService mExecutor = Executors.newCachedThreadPool();
        Future<String> downloadFutre = mExecutor.submit(callTask);
        try {
           // List<Runnable> runnableList= mExecutor.shutdownNow();
            // 执行时间小于3000会触发等待超时异常
            String url = downloadFutre.get(4000, TimeUnit.MILLISECONDS);
            boolean isDown = downloadFutre.isDone();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            //执行时间大于等待时间，抛出超时异常
        }
    }
}
