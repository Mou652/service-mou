package com.mou.controller;

import com.mou.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 *
 * @author: mou
 * @date: 2019-08-24
 */
@RestController
@Slf4j
public class LogController {

    @Log(value = "记录日志")
    @RequestMapping("/aop")
    public String aop(){
        return null;
    }
}
