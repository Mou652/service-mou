package com.mou.hutoolall.date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        log.info("当前时间:{}",time);
        log.info("当前季节:{}",time.quarterEnum().getValue());
        Console.log("测试");
    }
}
