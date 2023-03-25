package com.star.sky.common.utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static ExecutorService workThread = new ThreadPoolExecutor(
            5, 50,
            300L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new BasicThreadFactory.Builder().namingPattern("work-thread-[%d]").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

}
