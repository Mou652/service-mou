package com.mou.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * shiro测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTest01 {

    /**
     * 登录测试
     * <p>
     * 1.从配置文件(模拟数据库的用户信息)创建SecurityManagerFactory
     * 2.通过工厂获取SecurityManager
     * 3.从SecurityManager绑定当前运行环境
     * 4.从当前运行环境构建subject
     * 5.构造shiro登录数据
     * 6.主体登录
     */
    @Test
    public void testLogin() {
        //读取配置文件(模拟数据库用户信息)
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-auth.ini");
        //创建securityManager,绑定到当前运行环境
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //构建subject
        Subject subject = SecurityUtils.getSubject();
        //构造shiro登录数据
        String username = "lisi";
        String password = "tanfumjc";
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //主体登录
        subject.login(token);
        //验证用户是否登录成功
        System.out.println("用户是否登录成功" + subject.isAuthenticated());
        //获取等可以成功的数据
        System.out.println(subject.getPrincipal());

    }
}
