package com.zw.lock.synchronize;

/**
 * @author zhouwei
 * @date 2020-22-21:25
 */
public class SuperTest {
    private  String name ="A";

    public SuperTest(){
        super();
        System.out.println("B");
    }
}
class  Test extends SuperTest{
    private static String const1="C";
    public Test(){
        super();
        System.out.println("D");
    }

    public static void main(String[] args) {
        new Test();
    }
}
