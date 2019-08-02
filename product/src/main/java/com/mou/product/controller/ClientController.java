package com.mou.product.controller;

import com.mou.product.config.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
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

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        //第一张方式调用
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:9000/msg", String.class);
        //第二种方式
//        ServiceInstance product = loadBalancerCli
//        ent.choose("PRODUCT");
//        String url = String.format("http://%s:%s", product.getHost(),product.getPort()) + "/msg";
//        String response = restTemplate.getForObject(url, String.class);
        //第三种方式
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        return "hello";
    }
}
