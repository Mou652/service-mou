package com.mou.shiro.controller;

import com.mou.shiro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //使用shiro注解鉴权
    //RequiresPermissions 访问此方法必须具备的权限
    //requiresRoles 访问此方法必须具备的角色
    //缺点:注解方法会抛出AuthorizationException异常
    @RequiresPermissions("user-home")
    @RequestMapping("/user/home")
    public String home() {
        return "访问个人主页成功";
    }

    //添加
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String add() {
        return "添加用户成功";
    }

    //查询
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String find() {
        return "查询用户成功";
    }

    //更新
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String update(String id) {
        return "更新用户成功";
    }

    //删除
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete() {
        return "删除用户成功";
    }

    /**
     * 用户登录
     * shiro登录
     * 前端发成登录请求->接口请求获取用户名密码->通过subject.login->realm域的认证方式
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功的信息
     */
    @RequestMapping(value = "/login")
    public String login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "用户名或密码不能为空";
        }
        try {
            /*
             * 密码加密
             * shiro提供的md5加密
             * MD5Hash->参数1:加密的内容;参数2:加盐(用户登录名);参数三加密次数
             */
            password = new Md5Hash(password, username, 3).toString();
            //构建登录令牌
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //获取subject对象
            Subject subject = SecurityUtils.getSubject();
            //进行登录
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            return "登录失败,用户名或密码错误";
        }
    }

    @RequestMapping("/authError")
    public String authError(int code) {
        return code == 1 ? "未登录" : "未授权";
    }
}
