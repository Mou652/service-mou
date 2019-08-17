package com.mou.shiro.realm;

import com.mou.shiro.domain.Permission;
import com.mou.shiro.domain.Role;
import com.mou.shiro.domain.User;
import com.mou.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义realm域
 *
 * @author: mou
 * @date: 2019-08-17
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 指定realm域存放的位置
     *
     * @param name 域名称
     */
    @Override
    public void setName(String name) {
        super.setName("customRealm");
    }

    /**
     * 授权,必须进行认证
     * 操作的时候,判断用户是否具有相应的权限
     * 先认证 -- 安全数据
     * 在授权 -- 根据安全数据获取用户具有的操作权限
     *
     * @param principalCollection 安全数据
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取认证的安全数据
        User user = (User) principalCollection.getPrimaryPrincipal();
        //获取所有权限信息(角色,权限)
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //所有角色
        Set<String> roles = new HashSet<>();
        //所有权限
        Set<String> perms = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission perm : role.getPermissions()) {
                perms.add(perm.getCode());
            }
        }
        //设置角色和权限信息
        info.setStringPermissions(roles);
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken 传递过来的用户名和密码
     * @return 是否登录成功, true:登录成功;false登录失败
     * @throws AuthenticationException 登录失败抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //得到用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //使用Arrays.toString()进行字符串转换,避免空指针
        String password = new String(token.getPassword());

        //从数据库中查询用户信息
        User user = userService.findByName(username);
        if (user != null && user.getPassword().equals(password)) {
            //一致返回安全数据
            //构造方法:安全数据;密码;realm域名
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
        //不一致,返回null
        return null;
    }
}
