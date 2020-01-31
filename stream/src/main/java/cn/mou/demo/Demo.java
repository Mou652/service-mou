package cn.mou.demo;

import java.util.Arrays;
import java.util.List;

/**
 * @author: mou
 * @date: 2019/12/27
 */
public class Demo {

    public static void main(String[] args) {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList.stream() // 创建流
                .filter(s -> s.startsWith("c")) // 执行过滤，过滤出以 c 为前缀的字符串
                .map(s->s.toUpperCase()) // 转换成大写
                .sorted() // 排序
                .forEach(System.out::println); // for 循环打印
    }
}
