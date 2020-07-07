package com.zw.thread;

import java.io.*;

/**
 * @author zhouwei
 * 方式一：继承Thread类
 * 1.自定义类MyThread继承Thread类
 *
 * 2.MyThread类重写run方法。
 *
 * 3.创建线程对象
 * @date
 */
public class ThreadSeatwork1 {
    public static void main(String[] args) {
        // 创建线程对象
        MyThread mt = new MyThread(new File("ThreadSeatwork1.java"), new File("thread.txt"));
        mt.start();
    }
}

//1.自定义类MyThread继承Thread类

class MyThread extends Thread {

    private File srcFile;
    private File descFile;

    public MyThread() {
        super();
    }

    public MyThread(File srcFile, File descFile)  {

    }

    // 2.MyThread类重写run方法。
    @Override
    public void run() {
        copyFile(srcFile, descFile);
    }

    public void copyFile(File sreFile, File descFile) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(descFile));) {
            int len = 0;
            byte[] bys = new byte[1024];
            while ((len = bis.read(bys)) != -1) {
                bos.write(bys, 0, len);
                bos.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public File getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(File srcFile) {
        this.srcFile = srcFile;
    }

    public File getDescFile() {
        return descFile;
    }

    public void setDescFile(File descFile) {
        this.descFile = descFile;
    }
}
