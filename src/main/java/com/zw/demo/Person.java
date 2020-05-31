package com.zw.demo;

/**
 * @author zhouwei
 * @date 2020-20-0:34
 */
public class Person {

    int id; //编号

    String name; //姓名

    int age;  //年龄

    //构造函数
    public Person(int id, String name, int age) {

        this.id = id;
        this.name = name;
        this.age = age;
    }

    //比较年龄的方法，这是本身就具备的，还有一个和谁比较是未知数
    public void compareAge(Person p2) {

        if (this.age > p2.age) {
            System.out.println(this.age);
            System.out.println(p2.age);
            System.out.println(this.name + "大!");
        } else if (this.age < p2.age) {
            System.out.println(p2.name + "大!");
        } else {
            System.out.println("同龄");
        }
    }
}

class Demo7 {

    public static void main(String[] args) {

        Person p1 = new Person(110, "狗娃", 17);
        Person p2 = new Person(119, "铁蛋", 9);
        p1.compareAge(p2);
    }
}