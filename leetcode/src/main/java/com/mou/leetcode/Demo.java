package com.mou.leetcode;

/**
 * @author: mou
 * @date: 2019/12/21
 */
public class Demo {
    private String str = "成员变量";

    static {
        System.out.println("执行静态方法");
    }

    public Demo() {
        System.out.println("执行了构造方法");
        System.out.println("成员变量:"+str);
    }

    public static void main(String[] args) {
        System.out.println("");
        Demo demo = new Demo();
    }
}
