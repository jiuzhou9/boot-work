package com.jiuzhou.bootwork.threadpool;

import junit.framework.TestCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/14
 */
public class TestThreadPool extends TestCase{

    /**
     * 多线程
     */
    public void test(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            final int index = i;
            //            try {
            //                Thread.sleep(index * 1000);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("do some things");
                }
            });
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
