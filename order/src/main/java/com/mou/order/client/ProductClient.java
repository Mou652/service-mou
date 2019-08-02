package com.mou.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: mou
 * @date: 2019-07-30
 */
@FeignClient(name="PRODUCT")
public interface ProductClient {

    @GetMapping("/msg")
    String prodcutMsg();
}
