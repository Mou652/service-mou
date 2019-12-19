package com.itheima.hchat.controller;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.Result.Result;
import com.itheima.hchat.pojo.vo.User.User;
import com.itheima.hchat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mou
 * @date: 2019/10/13
 */
@AllArgsConstructor
@RestController("/user")
public class UserController {

    private UserService userService;

    @RequestMapping("/findAll")
    public List<TbUser> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/login")
    public Result login(@RequestBody TbUser user) {
        User _user = userService.login(user.getUsername(), user.getPassword());
        try {
            if (_user == null) {
                return new Result(false, "登录失败,检查用户名或密码是否输入正确");
            } else {
                return new Result(true, "登录成功", _user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "登录失败");
        }
    }

    @RequestMapping("/register")
    public Result register(@RequestBody TbUser User) {
        //如果注册未成功,抛出异常
        userService.register(User);
        return new Result(true, "注册成功");
    }

    @RequestMapping("/findByUsername")
    public Result findByUsername(String userId, String friendUsername) {
        User user = userService.findByUsername(userId, friendUsername);
        if (user != null) {
            return new Result(true, "搜索成功", user);
        } else {
            return null;
        }
    }
}
