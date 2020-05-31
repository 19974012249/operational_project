package com.zw.spring;

/**
 * @author zhouwei
 * @date 2020-26-23:51
 */
public class Demo01 {

    public static void main(String[] args) {

    }

}

/**
 * 饿汉式
 */
class SingleTonOne {

    public final static SingleTonOne singleTonOne = new SingleTonOne();

    private SingleTonOne() {

    }

    public static SingleTonOne getSingleton() {

        return singleTonOne;
    }
}

/**
 * 懒汉式
 */
class SingletonTwo {

    private static SingletonTwo singletonTwo = null;

    private SingletonTwo() {

    }

    public static SingletonTwo getSingletonTwo() {

        if (singletonTwo == null) {
            // 针对多线程
            singletonTwo = new SingletonTwo();
        }
        return singletonTwo;
    }
}

/**
 * 懒汉式加上同步锁
 */
class SingletonThree {

    private static SingletonThree singletonThree = null;

    private SingletonThree() {

    }

    public static SingletonThree getSingletonThree() {

        synchronized (SingletonThree.class) {
            if (singletonThree == null) {
                singletonThree = new SingletonThree();
            }
        }
        return singletonThree;
    }
}

/**
 * 双重锁校验
 */
class SingletonFour {

    private static SingletonFour singletonFour = null;

    private SingletonFour() {

    }

    public static SingletonFour singletonFour() {

        if (singletonFour == null) {
            synchronized (SingletonFour.class) {
                if (singletonFour == null) {
                    singletonFour = new SingletonFour();
                }
            }
        }
        return singletonFour;
    }
}

/**
 * 静态类部类
 * 优点：实现简单，懒加载，线程安全
 * 缺点：增加了一个静态内部类，apk文件过大
 */
class SingletonFive {
    private static class SingleTonFiveHolder{
        public static SingletonFive singletonFive = new SingletonFive();
    }
    private SingletonFive(){

    }
    // 只有调用该方法的时候才会返回实例对象
    public SingletonFive getSingletonFive(){
        return SingleTonFiveHolder.singletonFive;
    }
}

/**
 * 枚举
 */
enum SingleTonSix{

}

