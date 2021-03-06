package cn.moblog.multithread.insertDemo.web;

import cn.moblog.multithread.insertDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/multiThread")
    public String multiThread() {
        return userService.multiThread();
    }

    @RequestMapping(value = "/strInsert")
    public void strInsert() {
        userService.strInsert();
    }

}