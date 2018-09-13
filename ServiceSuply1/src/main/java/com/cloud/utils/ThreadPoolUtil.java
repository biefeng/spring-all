package com.cloud.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/12 22:38
 */
public class ThreadPoolUtil {

    private static final int corePoolSize = 20;

    private static final int maxPoolSize = 20;

    private static final ExecutorService SERVICE;

    private static final Set<Integer> NUMBERS = new HashSet<>();

    private static AtomicInteger threadIndex = new AtomicInteger(0);


    static {
        SERVICE = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new TaskThreadFactory());
    }

    private ThreadPoolUtil(){}

    private static class TaskThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Task-Thread-" + threadIndex.incrementAndGet());
            t.setUncaughtExceptionHandler(new TaskUncaughtExceptionHandler());

            return t;
        }
    }

    private static class TaskUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            t.interrupt();
            e.printStackTrace();
        }
    }

    public static ExecutorService getService(){
        return SERVICE;
    }
}
