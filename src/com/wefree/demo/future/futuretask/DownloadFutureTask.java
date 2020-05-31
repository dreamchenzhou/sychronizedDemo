package com.wefree.demo.future.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class DownloadFutureTask extends FutureTask<String> {
    public DownloadFutureTask(Runnable runnable, String result) {
        super(runnable, result);
    }

    public DownloadFutureTask(Callable<String> callable){
        super(callable);
    }
}
