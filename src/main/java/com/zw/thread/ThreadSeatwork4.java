package com.zw.thread;

/**
 * @author zhouwei
 * 方法四：匿名内部类 开启多线程
 * @date
 */
public class ThreadSeatwork4 {
    public static void main(String[] args) {
        new Thread();
        new Thread().start();
//		方式一继承Thread开启线程
        new Thread() {}.start();
        new Thread() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("方法一继承Thread开启线程" + i);
                }
            };
        }.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("方式二实现Runnable开启线程" + i);
                }
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            System.out.println("主方法开启线程" + i);
        }

//		如果一个线程既继承了Thread，同时实现了Runnable接口，那么继承Thread优先
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("方式二实现Runnable开启线程---" + i);
                }
            }
        }) {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("方法一继承Thread开启线程---" + i);
                }
                super.run();
            }
        }.start();
    }
}
