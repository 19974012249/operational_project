package com.zw.algorithm.introduction;

/**
 * 入门：判断是否为回文字符串
 *
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-24 14:30
 * @since 0.1.0
 **/
public class Demo141 {

    public static void main(String[] args) {

        System.out.println(5 / 2);
    }

    /**
     * 1、用二分法判断
     * 2、用反串的比较
     * 3、循环比较
     *
     * @param str
     * @return
     */
    public Boolean judgeTest(String str) {

        if (str.length() == 0) {
            return false;
        }
        char[] chars = str.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            if (chars[i] != chars[length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 二分法判断
     *
     * @param str
     * @return
     */
    public static Boolean judge001(String str) {

        if (str.length() == 0) {
            return false;
        }
        char[] chars = str.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            if (chars[i] != chars[length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 双指针判断
     *
     * @param str
     * @return
     */
    public static Boolean judge002(String str) {
        //首指针
        int left = 0;
        //尾指针
        int right = str.length() - 1;
        //首尾往中间靠
        while (left < right) {
            //比较前后是否相同
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * 字符串反转判断
     *
     * @param str
     * @return
     */
    public static Boolean judge003(String str) {

        StringBuffer stringBuffer = new StringBuffer(str);
        if (str.equals(stringBuffer.reverse().toString())) {
            return true;
        }
        return false;
    }
}
