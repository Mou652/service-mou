package com.mou.demo;
/**
 * @author: mou
 * @date: 2020/1/11
 */
public class VolatileDemo {
    public static boolean flag = true;

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {

        }
    }


}
