package com.mou.quartz.deferred;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author: mou
 * @date: 2022/4/17
 */
public class Test {


    public static void main(String[] args) throws InterruptedException {
        Consumer<List<String>> consumer = list -> System.out.println(list.size());
        BatchCommon<String> stringBatchCommon = new BatchCommon<>(5, 0, consumer);
        while (true) {
            Thread.sleep(100);
            stringBatchCommon.add("");
        }
    }
}
