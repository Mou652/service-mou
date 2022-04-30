package cn.moblog.multithread.demo;

import cn.hutool.core.util.StrUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;

/**
 * 并发情况下保证数据的原子性
 *
 * @author: mou
 * @date: 2020/3/2
 */
@SuppressWarnings("all")
public class Demo {
    LongAdder longAdderCollect = new LongAdder();
    static LongAdder longAdderUse = new LongAdder();

    public void demo() {

        synchronized (longAdderCollect) {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        test((true));
                        test((false));
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println(StrUtil.format("收藏人数:{}", longAdderCollect.toString()));
        System.out.println(StrUtil.format("访问人数:{}", longAdderUse.toString()));
    }

    /**
     * 并发情况下更新访问人数
     */

    public static void test(boolean isflag) {
        //后续访问,人数递增
        if (isflag) {
            longAdderCollect.increment();
        } else {
            longAdderUse.increment();
        }
    }
}
