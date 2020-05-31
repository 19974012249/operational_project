package com.zw.ruantong.suanfa.digui;

/**
 * @author zhouwei
 * @date 2020-21-21:00
 */
public class Demo01 {
    //递归
    public static int recursion(int num){
        if (num <= 1){
            return 1;
        } else {
            return num * recursion(num - 1);
        }
    }

    public static void main(String[] args) {
        int result = recursion(5);
        System.out.println(result);
    }
}
