package cn.moublog;

/**
 * @author: mou
 * @date: 2021/2/16
 */
public class Demo4 {
    public static void main(String[] args) {
        System.out.println(fib(10));
    }

    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return (fib(n - 1) + fib(n - 2)) % 10 ^ 9 + 7;
    }
}
