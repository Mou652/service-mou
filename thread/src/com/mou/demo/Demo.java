package com.mou.demo;

import static com.mou.demo.UserIdentityEnum.STUDENT;

/**
 * @author: mou
 * @date: 2020/2/4
 */
public class Demo {
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        System.out.println(demo1.userIdentityEnum.name());
        System.out.println(demo1.userIdentityEnum.toString());
    }
    static class Demo1{
        UserIdentityEnum userIdentityEnum=STUDENT;
    }
}
