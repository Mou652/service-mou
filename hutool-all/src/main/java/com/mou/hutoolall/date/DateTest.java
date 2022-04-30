package com.mou.hutoolall.date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: mou
 * @date: 2019-08-21
 */
@Slf4j
public class DateTest {

    @Test
    public void test() {
        DateTime time = DateTime.of(new Date());
        log.info("当前时间:{}", time);
        log.info("当前季节:{}", time.quarterEnum().getValue());
        Console.log("测试");
    }

    @Test
    public void test1() throws InterruptedException {
        // 流程开始时间
        DateTime startDate = DateUtil.date();
        Thread.sleep(10);
        // 当前时间
        DateTime currentDate = DateUtil.date();

        long between = DateUtil.between(currentDate, startDate, DateUnit.SECOND, false);
        System.out.println(between);
    }

    @Test
    public void test2() {
        System.out.println(DateUtil.today());
        System.out.println(DateUtil.hour(new Date(), true));
        System.out.println(DateUtil.yesterday());
        System.out.println(DateUtil.yesterday().toString().split(" ")[0]);
    }

    @Test
    public void test3() {
        System.out.println(BigDecimal.valueOf(1).compareTo(BigDecimal.ZERO) != 0);

        ArrayList<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        System.out.println(list.indexOf(2L));
    }

    @Test
    public void test4() {


        DateTime date = DateUtil.date();
        System.out.println(DateUtil.beginOfDay(date));
        System.out.println(DateUtil.endOfDay(date));

        System.out.println(111 % 2);
        System.out.println(112 % 2);
        System.out.println(113 % 2);
        System.out.println(997 % 2);
        System.out.println(995 % 2);
    }
}
