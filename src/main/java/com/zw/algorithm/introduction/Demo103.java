package com.zw.algorithm.introduction;

import java.util.Arrays;

/**
 * 入门： 反转字符串
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-24 11:58
 * @since 0.1.0
 **/
public class Demo103 {

    public static void main(String[] args) {

        String str = "abcde";

        char[] ans = str.toCharArray();
        //        String[] split = str.split("");
        //        System.out.println(Arrays.toString(split));
        System.out.println(Arrays.toString(ans));
        System.out.println(new String(ans));
    }

    /**
     * 测试
     *
     * @param str
     * @return
     */
    public static String reversal001(String str) {

        String[] split = str.split("");
        if (split.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuilder.append(split[i]);
        }
        String s = stringBuilder.toString();
        return s;
    }

    /**
     * 循环遍历
     *
     * @param str
     * @return
     */
    public static String reversal001002(String str) {

        String[] split = str.split("");
        if (split.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            stringBuilder.append(split[i]);
        }
        String s = stringBuilder.toString();
        return s;
    }

    /**
     * 官方答案
     *
     * @param str
     * @return
     */
    public static String reversal001003(String str) {

        char[] ans = str.toCharArray();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            ans[i] = str.charAt(len - 1 - i);
        }
        return new String(ans);
    }


    /**
     * 官方答案2
     *
     * @param str
     * @return
     */
    public String reversal001004(String str) {

        char[] cstr = str.toCharArray();
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            char t = cstr[i];
            cstr[i] = cstr[len - 1 - i];
            cstr[len - 1 - i] = t;
        }
        return new String(cstr);
    }
}
