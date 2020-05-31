package com.zw.lock.synchronize;

/**
 * @author zhouwei
 * @date 2020-22-22:38
 */
public class father {

    private String name;

    father(){
        System.out.println("--父类的无参构造函数--");
    }

    father(String name){
        this.name=name;
        System.out.println("--父类的有参构造函数--"+this.name);
    }

    static{
        System.out.println("--父类的静态代码块--");
    }

    {
        System.out.println("--父类的非静态代码块--");
    }

    public void speak(){
        System.out.println("--父类的方法--");
    }

    public static void main(String[] args) {
        System.out.println("--父类主程序--");
        father father=new father("父亲的名字");
        father.speak();
    }

}
