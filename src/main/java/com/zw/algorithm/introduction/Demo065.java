package com.zw.algorithm.introduction;

/**
 * 入门： 斐波那契数列
 *
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-24 11:03
 * @since 0.1.0
 **/
public class Demo065 {

    public static void main(String[] args) {

        int num = 5;
        for (int i = 1; i <= 40; i++) {
            if (test(i) != Fibonacci(i)) {
                System.out.println("数据不正确");
            }
        }
        //        System.out.println(test(num));
        //        System.out.println(Fibonacci(num));
    }

    public static int test(int n) {

        if (n <= 1) {
            return n;
        }
        int total = 0;
        int pre = 0;
        int next = 1;
        for (int i = 2; i <= n; i++) {
            total = pre + next;
            pre = next;
            next = total;
        }
        return total;
    }

    /**
     * 斐波那契列
     */
    public static int Fibonacci(int n) {
        //从0开始，第0项是0，第一项是1
        if (n <= 1) {
            return n;
        }
        // 合
        int res = 0;
        int a = 0;
        int b = 1;
        //因n=2时也为1，初始化的时候把a=0，b=1
        for (int i = 2; i <= n; i++) {
            //第三项开始是前两项的和,然后保留最新的两项，更新数据相加
            res = (a + b);
            a = b;
            b = res;
        }
        return res;
    }


}
