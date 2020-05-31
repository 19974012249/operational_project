package com.zw.volatil;

/**
 * @author zhouwei
 * @date 2020-14-23:34
 */
public class Counter {
    private volatile int count = 0;
    public void inc() {
        try {
            Thread.sleep(3);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
    }
    @Override
    public String toString() {

        return "[count=" + count + "]";
    }
}
class VolatileTest {

    public static void main(String[] args) {

        final Counter counter = new Counter();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }
        System.out.println(counter);
    }
}
