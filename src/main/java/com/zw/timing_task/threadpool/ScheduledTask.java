package com.zw.timing_task.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouwei
 * @date 2020-14-20:27
 */
public class ScheduledTask {

    public static void main(String[] args) {

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                System.out.println("定时业务逻辑");
            }
        }, 1000, 3000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                System.out.println("定时业务逻辑2" + System.currentTimeMillis());
            }
        }, 1000, 3000, TimeUnit.MILLISECONDS);
    }
}
