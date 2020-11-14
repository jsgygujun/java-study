package com.jsgygujun.example.字符串常量Java内部加载;

/**
 * @author GuJun
 * @date 2020/11/14
 */
public class Demo1 {

    public static void main(String[] args) {
        案例1();
    }

    public static void 案例1() {
        String str1 = new StringBuilder("计算机").append("书籍").toString();
        System.out.println(str1); // 58tongcheng
        System.out.println(str1.intern()); // 58tongcheng
        System.out.println(str1 == str1.intern()); // true

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2); // java
        System.out.println(str2.intern()); // java
        System.out.println(str2 == str2.intern()); // false，
        // 分析： 原因必然存在两个"java"，那另一个字符串如何加载进来的？
        // 有一个初始化的Java字符串（JDK自带的），在加载sun.misc.Version这个类的时候进入常量池子。
    }
}
