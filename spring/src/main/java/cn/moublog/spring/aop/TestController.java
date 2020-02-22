package cn.moublog.spring.aop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    public void testFunc(String userName) {
        //do something
        System.out.println("Hello " + userName);
    }

}
