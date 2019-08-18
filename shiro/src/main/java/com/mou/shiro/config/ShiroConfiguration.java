package com.mou.shiro.config;

import com.mou.shiro.realm.CustomRealm;
import com.mou.shiro.session.CustomSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 *
 * @author: mou
 * @date: 2019-08-17
 */
@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    /**
     * 创建realm
     */
    @Bean
    public CustomRealm getRealm() {
        return new CustomRealm();
    }

    /**
     * 创建安全管理器
     */
    @Bean
    public SecurityManager getSecurityManager(CustomRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        //将自定义的会话管理器注册到安全管理器
        securityManager.setSessionManager(getSessionManager());
        //将自定义redis缓存管理器注册到安全管理器中
        securityManager.setCacheManager(getRedisCacheManager());
        return securityManager;
    }

    /**
     * 配置shiro的过滤器工厂
     * shiro权限控制都是一组过滤器实现
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();

        //设置安全管理器
        filterFactory.setSecurityManager(securityManager);

        //通用配置(跳转登录页面,为授权跳转的页面)
        //未登录时跳转的url
        filterFactory.setLoginUrl("/authError/1");
        //未授权的url
        filterFactory.setUnauthorizedUrl("/authError/2");

        /*
          设置所有过滤器:map
          key=拦截的url地址
          value=过滤器类型
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        //不设置权限,当前请求地址.可以匿名访问
//        filterMap.put("/user/home", "anon");

        //1.具有某种权限才能访问
//        filterMap.put("/user/home","perms[user-home]");
        //2.具有某种角色才能访问
//        filterMap.put("user/home","roles[普通员工]");

        //必须认证之后才可以访问
        filterMap.put("/user/**", "authc");
        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }

    /**
     * redis控制器,操作redis
     */
    public RedisManager getRedisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    /**
     * sessionDao
     */
    public RedisSessionDAO getRedisSessionDao() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(getRedisManager());
        return sessionDAO;
    }

    /**
     * 自定义会话管理器
     */
    public DefaultWebSessionManager getSessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(getRedisSessionDao());

        return customSessionManager;
    }

    /**
     * 缓存管理器
     */
    @Bean
    public RedisCacheManager getRedisCacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(getRedisManager());
        return cacheManager;
    }

    /**
     * 开启对shiro注解的支持
     *
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
