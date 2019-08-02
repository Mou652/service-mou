package com.mou.order.controller;

import com.mou.order.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: mou
 * @date: 2019-07-29
 */
@RestController
public class ClientController {

//    @Autowired
//    private ProductClient productClient;
//
//    @GetMapping("/getProductMsg")
//    public String getProductMsg() {
//
//        return productClient.prodcutMsg();
//    }
}
