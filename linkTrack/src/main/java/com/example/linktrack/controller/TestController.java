package com.example.linktrack.controller;

import cn.langpy.kotime.annotation.ComputeTime;
import com.example.linktrack.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: mou
 * @date: 2022/2/27
 */
@RequestMapping("/linkTrack")
@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @ComputeTime
    @GetMapping("test")
    public String test() {
        Long userId = 1L;
        log.info("controller->userId:{}", userId);
        testService.test(userId);
        return "请求成功";
    }
}
