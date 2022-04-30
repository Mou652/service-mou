package ecs.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: mou
 * @date: 2019/10/26
 */
@RestController
@RequestMapping("yongzhuo")
public class Demo {

    @RequestMapping("/test")
    public String test() {
        return "雍卓斯基";
    }

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("500");
        boolean flag = amount.compareTo(BigDecimal.valueOf(500)) >= 0;
        System.out.println(flag);
    }
}
