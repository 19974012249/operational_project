package com.zw.thread;

/**
 * @author zhouwei
 * 方式二：实现Runnable接口
 *
 * 1.自定义类MyRunnable实现Runnable接口
 *
 * 2.重写run（）方法
 *
 * 3.创建MyRunnable类的对象
 *
 * 4.创建Thread类的对象，并把步骤3创建的对象作为构造参数传递
 *
 * 5.启动线程
 * @date
 */
public class ThreadSeatwork2 {
    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();
        Thread t = new Thread(mr);
        t.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("主线程" + i);
        }
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("子线程" + i);
        }
    }
}
