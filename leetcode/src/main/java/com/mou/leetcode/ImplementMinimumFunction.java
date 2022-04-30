package com.mou.leetcode;

import cn.hutool.core.util.ArrayUtil;

/**
 * 自己设计一个泛型的获取数组最小值的函数.并且这个方法只能接受Number的子类并且实现了Comparable接口。
 *
 * @author: mou
 * @date: 2020/4/19
 */
public class ImplementMinimumFunction {

    public static void main(String[] args) {
        Integer[] arrayInt = {1, 2, 3, 7, 4};
        System.out.println(min(arrayInt));
    }

    public static <T extends Number & Comparable<? super T>> T min(T[] value) {
        if (ArrayUtil.isEmpty(value)) {
            return null;
        }
        T min = value[0];
        for (T t : value) {
            if (min.compareTo(t) > 0) {
                min = t;
            }
        }
        return min;
    }
}
