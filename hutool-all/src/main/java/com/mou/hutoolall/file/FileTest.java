package com.mou.hutoolall.file;

import cn.hutool.core.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * @author: mou
 * @date: 2019-08-21
 */
@Slf4j
public class FileTest {

    @Test
    public void test(){
        File file = new File("/Users/mou/Pictures/Snip20190731_2.png");
        log.info("文件类型:{}", FileTypeUtil.getType(file));

    }
}
