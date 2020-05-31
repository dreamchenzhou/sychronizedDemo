package com.wefree.demo.future.callable;

import java.util.concurrent.Callable;

public class DownloadCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        String url = "http://www.baidu.com";
        Thread.sleep(3*1000);
        return url;
    }
}
