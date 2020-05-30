package com.zw.test;

/**
 * @author zhouwei
 * @date 2020-26-21:38
 */
public class TestString {

    public static void main(String[] args) {

        String s4 = "ab" + "c";
        String s = new String("ab") + "c";
        String s1 = "c";
        String s2 = "ab";
        String s3 = new String("ab");
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s3.intern()));
        System.out.println("s == s4:" + (s == s4));
        System.out.println("s.intern == s4:" + (s.intern() == s4));
        System.out.println("s2 == s3:" + (s2 == s3));
        System.out.println("s2 == s3.intern:" + (s2 == s3.intern()));
    }
}
