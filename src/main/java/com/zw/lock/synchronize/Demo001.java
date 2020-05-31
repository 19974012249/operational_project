package com.zw.lock.synchronize;

/**
 * @author zhouwei
 * @date 2020-19-21:45
 */
public class Demo001 {
    public synchronized String getDemo(){
        return null;
    }
    public String getDemo02(){
        synchronized (this){
            System.out.println("");
            return null;
        }
    }
}
