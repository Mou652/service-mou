package com.mou.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: mou
 * @date: 2019-07-29
 */
@RestController
public class ServerController {

    @GetMapping("/msg")
    public String msg() {
        return "this is product ' msg '";
    }
}
