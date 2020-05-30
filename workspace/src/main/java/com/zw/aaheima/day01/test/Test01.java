package com.zw.aaheima.day01.test;

import java.util.Objects;

/**
 * @author zhouwei
 * @date 2020-17-17:10
 * - public String toString()：返回该对象的字符串表示。
 * <p>
 * toString方法返回该对象的字符串表示，其实该字符串内容就是对象的类型+@+内存地址值。
 * <p>
 * 由于toString方法返回的结果是内存地址，而在开发中，经常需要按照对象的属性得到相应的字符串表现形式，因此也需要重写它。
 */
public class Test01 {

    public static void main(String[] args) {
        // 创建对象obeject
        Object obj = new Object();
        // 生成变量接受方法的返回值
        String s = obj.toString();
        System.out.println(s);
        Person person = new Person();
        person.name = "zhouwei";
        person.age = 27;
        int i = person.hashCode();
        System.out.println(Integer.toHexString(i));
        System.out.println(i);
        System.out.println(person.toString());
    }

}

class Person {

    String name;

    int age;

//    @Override
//    public String toString() {
//
//        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
//    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age);
    }
}
