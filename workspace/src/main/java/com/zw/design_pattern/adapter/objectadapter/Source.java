package com.zw.design_pattern.adapter.objectadapter;

/**
 * 对象适配器
 * @author zhouwei
 * @date 2020-22-19:44
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

class Wrapper implements Targetable {

    private Source source;

    public Wrapper(Source source) {

        super();
        this.source = source;
    }

    @Override
    public void method2() {

        System.out.println("this is the targetable method!");
    }

    @Override
    public void method1() {

        source.method1();
    }
}

class AdapterTest {

    public static void main(String[] args) {

        Source source = new Source();
        Targetable target = new Wrapper(source);
        target.method1();
        target.method2();
    }
}
