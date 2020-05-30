package com.zw.ruantong.suanfa.digui;

/**
 * @author zhouwei
 * @date 2020-21-21:05
 */
public class Demo02 {

    public static void main(String[] args) {

        for (int i = 1; i < 11; i++) {
            System.out.println("第" + i + "天" + rount(i) + "只兔子");
        }
    }

    private static int rount(int num) {

        if (num < 3) {
            return 1;
        } else {
            return rount(num - 2) + rount(num - 1);
        }
    }
}
