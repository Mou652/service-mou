package com.example.linktrack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: mou
 * @date: 2022/2/27
 */
@Slf4j
@Service
public class TestService {

    public void test(Long userId) {
        log.info("service->userId:{}", userId);
    }
}
