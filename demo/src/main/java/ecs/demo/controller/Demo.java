package ecs.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
