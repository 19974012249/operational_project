package com.zw.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhouwei
 * @date 2020-30-22:30
 */
public class Demo {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i + "", i + "AA");
        }
        // 38ms 1.1、keySet的for循环，只获取key
        // keySetForGetKey(map);

        // 29ms 1.2、keyset获取key和value
        // keySetForGetKeyAndValue(map);

        // 2.1、keySet的iterator迭代器方式：只获取key
        // keySetIteratorGetKey(map);

        // 2.2、keySet的iterator迭代器方式，获取key和value
        // keySetIteratorGetKeyAndValue(map);

        // 3.1、entrySet的for循环方式：只获取key
        // entrySetForGetKeyAndValue(map);

        // 3.2、entrySet的for循环方式：获取key和value
        // entrySetForGetKeyAndValue(map);

        // 4.1、entrySet的iterator迭代器方式：获取key
        // entrySetIteratorGetKey(map);
        // 4.2、entrySet的iterator迭代器方式：获取key和value
        // entrySetIteratorGetKeyAndValue(map);
    }

    /**
     * 1.1、keySet的for循环，只获取key
     *
     * @param map
     */
    public static void keySetForGetKey(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        for (String key : map.keySet()) {
            System.out.println(key);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("keySetForGetKey运行时间" + (endTime - startTime));
    }

    //获取key和value

    /**
     * 1.2、keyset获取key和value
     *
     * @param map
     */
    public static void keySetForGetKeyAndValue(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println(key);
            // System.out.println(key + ":" + value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("keySetForGetKeyAndValue运行时间" + (endTime - startTime));
    }

    /**
     * 2.1、keySet的iterator迭代器方式：只获取key
     *
     * @param map
     */
    public static void keySetIteratorGetKey(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("keySetIteratorGetKey运行时间" + (endTime - startTime));
    }

    /**
     * 2.2、keySet的iterator迭代器方式，获取key和value
     *
     * @param map
     */
    public static void keySetIteratorGetKeyAndValue(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(iterator.next());
            System.out.println(key + ":" + value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("keySetIteratorGetKeyAndValue运行时间" + (endTime - startTime));
    }

    /**
     * 3.1、entrySet的for循环方式：只获取key
     *
     * @param map
     */
    public static void entrySetForGetKey(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("entrySetForGetKey运行时间" + (endTime - startTime));
    }

    /**
     * 3.2、entrySet的for循环方式：获取key和value entrySetForGetKeyAndValue
     *
     * @param map
     */
    public static void entrySetForGetKeyAndValue(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + ":" + value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("entrySetForGetKeyAndValue运行时间" + (endTime - startTime));
    }

    /**
     * 4.1、entrySet的iterator迭代器方式：只获取key
     *
     * @param map
     */
    public static void entrySetIteratorGetKey(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            System.out.println(key);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("entrySetIteratorGetKey运行时间" + (endTime - startTime));
    }

    /**
     * 4.2、entrySet的iterator迭代器方式：获取key和value
     *
     * @param map
     */
    public static void entrySetIteratorGetKeyAndValue(Map<String, String> map) {

        long startTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            String value = iterator.next().getValue();
            System.out.println(key + ":" + value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("entrySetIteratorGetKeyAndValue运行时间" + (endTime - startTime));
    }
}
