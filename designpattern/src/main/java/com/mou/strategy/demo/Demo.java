package com.mou.strategy.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author: mou
 * @date: 2020/3/4
 */
public class Demo {
    private static ThreadLocal<DateFormat> threadLocalDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd_HHmm"));
    public static void main(String[] args) {
        System.out.println(threadLocalDateFormat.get().format(LocalDateTime.now()));

    }
}
