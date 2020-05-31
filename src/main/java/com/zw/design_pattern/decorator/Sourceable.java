package com.zw.design_pattern.decorator;

/**
 * 装饰者设计模式
 * @author zhouwei
 * @date 2020-22-20:18
 */
public interface Sourceable {

    public void method();
}

class Source implements Sourceable {

    @Override
    public void method() {
        System.out.println("the original method!");
    }
}

class Decorator implements Sourceable {

    private Sourceable source;

    public Decorator(Sourceable source) {

        super();
        this.source = source;
    }

    @Override
    public void method() {

        System.out.println("before decorator!");
        source.method();
        System.out.println("after decorator!");
    }
}

class DecoratorTest {

    public static void main(String[] args) {

        Sourceable source = new Source();
        Sourceable obj = new Decorator(source);
        obj.method();
    }
}