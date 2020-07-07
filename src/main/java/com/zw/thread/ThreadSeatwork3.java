package com.zw.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author zhouwei
 * 实现Callable方式开启线程
 * 实现Runnable和实现Callable接口的区别
 * 1.有返回值
 *
 * 2.可以声明异常
 * 这里的返回值和异常抛出都是给到线程的启动者
 * @date
 */
public class ThreadSeatwork3 {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<>(new MyCallable(1, 100));
        Thread t = new Thread(task);
        t.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("main" + i);
        }
        try {
            Integer value = task.get();
            System.out.println(value);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("子线程抛出异常给主线程" + e);
            e.printStackTrace();
        }
        System.out.println("over");
    }
}

class MyCallable implements Callable<Integer> {
    private Integer m;
    private Integer n;

    public MyCallable() {
        super();
    }
    public MyCallable(Integer m, Integer n) {
        super();
        this.m = m;
        this.n = n;
    }


    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
            sum += i;
        }
        return sum;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }
}