package com.star.sky.member.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class UUIDUtilTest {

    private static final int THREAD_NUM = 30;
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    @Test
    public void testNextUUID() {
        for (int i = 0; i < 20; i++) {
            log.info("uuid is: {}", UUIDUtil.nextUUID());
        }
    }

    @Test
    public void testNextId() {
        ConcurrentHashMap<Long, Long> map = new ConcurrentHashMap<>(THREAD_NUM);
        List<Long> list = Collections.synchronizedList(new LinkedList<>());

        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(() -> {
                // 所有的线程在这里等待
                try {
                    countDownLatch.await();

                    Long id = UUIDUtil.nextId();
                    list.add(id);
                    map.put(id, 1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            thread.start();
            // 启动后，倒计时计数器减一，代表有一个线程准备就绪了
            countDownLatch.countDown();
        }

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("list is: {}", list.toString());
    }

}
