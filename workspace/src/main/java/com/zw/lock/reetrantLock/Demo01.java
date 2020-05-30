package com.zw.lock.reetrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhouwei
 * @date 2020-12-1:56
 */
public class Demo01 {

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        // 获取锁
        reentrantLock.lock();
        try {
            System.out.println("执行业务逻辑");
        } finally {
            // 释放锁
            reentrantLock.unlock();
        }
    }

}
