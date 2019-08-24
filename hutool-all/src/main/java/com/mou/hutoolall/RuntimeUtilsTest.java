package com.mou.hutoolall;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: mou
 * @date: 2019-08-21
 */
@Slf4j
public class RuntimeUtilsTest {

    @Test
    public void test(){
        String ip = RuntimeUtil.execForStr("ifconfig");
        log.info("ip地址:{}",ip);
    }
}
