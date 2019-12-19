package com.itheima.hchat.service;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.User.User;

import java.util.List;

/**
 * @author: mou
 * @date: 2019/10/13
 */
public interface UserService {

    /**
     * 查询
     *
     * @return
     */
    List<TbUser> findAll();

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 注册
     *
     * @param user
     */
    void register(TbUser user);

    /**
     * 好友搜索
     *
     * @param userId
     * @param friendUsername
     * @return
     */
    User findByUsername(String userId, String friendUsername);
}
