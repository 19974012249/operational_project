package com.zw.algorithm.introduction;

/**
 * 入门：最大公约数
 *
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-24 15:03
 * @since 0.1.0
 **/
public class Demo151 {

    public static void main(String[] args) {

        System.out.println(gcdTest(16, 12));
    }

    /**
     * 测试：
     * 1、
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcdTest(int a, int b) {
        // write code here
        int c = 0;
        while(a!=0){
            c = b%a;
            b = a;
            a = c;
        }
        return b;
    }

    /**
     * 辗转相除法
     * @param a
     * @param b
     * @return
     */
    public int gcd003 (int a, int b) {
        // write code here
        int c = 0;
        while(a!=0){
            c = b%a;
            b = a;
            a = c;
        }
        return b;
    }

    /**
     * 步骤：
     * 1、先确定最小的值
     * 2、再用最小的值循环遍历
     *
     * @param a
     * @param b
     * @return
     */
    public int gcd001(int a, int b) {
        // write code here
        // 确定最小的值
        int min = (a <= b) ? a : b;
        for (int i = min; i > 0; i--) {
            if (a % i == 0 && b % i == 0) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * <p>
     * 求出a、b的最大公约数。
     *
     * @param a int
     * @param b int
     * @return int
     */
    public int gcd002(int a, int b) {
        // write code here
        int min = (a < b) ? a : b;
        int max = 0;
        for (int i = min; i > 0; i--) {
            if (a % i == 0 && b % i == 0) {
                max = i;
                break;
            }
        }
        return max;
    }


}
