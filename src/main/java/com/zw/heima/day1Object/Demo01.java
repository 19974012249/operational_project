package com.zw.heima.day1Object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhouwei
 * @date 2020-31-11:43
 */
public class Demo01 {

    public static void main(String[] args) throws ParseException {

        Date date = new Date();
        // 创建日期格式化对象,在获取格式化对象时可以指定风格
        //DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//多态的写法
        //        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//子类对象的写法
        //        String str = df.format(date);//时间变成指定格式的字符串
        //        Date parse = df.parse(str);
        //        System.out.println("解析的时间格式:" + parse);
        //        System.out.println("格式化的时间格式：" + str); // 2008年1月23日
        //        System.out.println("当前时间：" + date);
        //        System.out.println("1970年1月1日8时0分0秒：" + new Date(0));
        System.out.println(Calendar.getInstance());
    }

}
