package com.xxx;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class Authenticator {
    public static void main(String[] args) {
        //  创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //  给安全管理器设置realm
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //  SecurityUtils给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //  关键对象subject主体
        Subject subject = SecurityUtils.getSubject();
        //  创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        try {
            System.out.println("认证状态1:"+subject.isAuthenticated());
            //  用户认证
            subject.login(token);
            System.out.println("认证状态2:"+subject.isAuthenticated());
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败，密码错误");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败，用户名不存在");
        }
    }
}
