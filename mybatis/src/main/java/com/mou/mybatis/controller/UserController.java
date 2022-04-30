package com.mou.mybatis.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mou.mybatis.annotation.Auth;
import com.mou.mybatis.entity.User;
import com.mou.mybatis.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mou
 * @date: 2021/12/11
 */
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    @Auth
    @GetMapping("/info")
    public List<User> getInfo() {
        return userMapper.selectList(Wrappers.lambdaQuery());
    }

}
