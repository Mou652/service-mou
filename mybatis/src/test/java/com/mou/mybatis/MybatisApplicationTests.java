package com.mou.mybatis;

import com.mou.mybatis.controller.UserController;
import com.mou.mybatis.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        List<User> users = userController.getInfo();
        System.out.println(users);
    }

}
