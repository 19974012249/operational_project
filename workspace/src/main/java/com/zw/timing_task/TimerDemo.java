package com.zw.timing_task;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhouwei
 * @date ${date} ${time}
 */
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("业务逻辑");
            }
        }, 3 * 1000, 1000);
    }

}
