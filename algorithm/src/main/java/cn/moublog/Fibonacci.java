package cn.moublog;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * 菲波那切数列
 *
 * @author: mou
 * @date: 2021/2/13
 */
public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(fib(10));
    }

    public static int fib(int n) {
        return (fib(n - 1) + fib(n - 2)) % 10 ^ 9 + 7;
    }
}
