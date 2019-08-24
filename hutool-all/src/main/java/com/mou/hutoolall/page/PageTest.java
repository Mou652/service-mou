package com.mou.hutoolall.page;

import cn.hutool.core.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: mou
 * @date: 2019-08-21
 */
@Slf4j
public class PageTest {

    @Test
    public void test(){
        int[] ints = PageUtil.transToStartEnd(1, 10);
        log.info("分页参数:{}",ints);
        //参数意义分别为：当前页、总页数、每屏展示的页数
        int[] rainbow = PageUtil.rainbow(11, 20, 10);
        log.info("彩虹分页:{}",rainbow);
    }
}