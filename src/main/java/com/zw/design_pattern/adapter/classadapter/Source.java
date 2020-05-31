package com.zw.design_pattern.adapter.classadapter;

/**
 * 类适配器
 * @author zhouwei
 * @date 2020-22-19:39
 */
public class Source {

    public void method1() {

        System.out.println("this is original method!");
    }
}

interface Targetable {
    /* 与原类中的方法相同 */


    public void method1();
    /* 新类的方法 */


    public void method2();
}

class Adapter extends Source implements Targetable {

    @Override
    public void method2() {

        System.out.println("this is the targetable method!");

    }
}

class AdapterTest {

    public static void main(String[] args) {

        Targetable target = new Adapter();
        target.method1();
        target.method2();
    }
}