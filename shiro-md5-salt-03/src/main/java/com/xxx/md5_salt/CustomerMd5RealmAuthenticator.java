package com.xxx.md5_salt;

import com.xxx.md5_salt.realm.CustomerMd5Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class CustomerMd5RealmAuthenticator{
    public static void main(String[] args) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        CustomerMd5Realm realm = new CustomerMd5Realm();
        //  设置realm使用hash凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //  声明：使用的算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //  声明：散列次数
        credentialsMatcher.setHashIterations(1024);

        realm.setCredentialsMatcher(credentialsMatcher);
        //  注入realm
        securityManager.setRealm(realm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");
        try {
            subject.login(token);
            System.out.println("登录成功~~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败，密码错误！");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败，账户不存在！");
        }

        //  授权
        if (subject.isAuthenticated()) {
            //  基于角色控制权限
            System.out.println(subject.hasRole("admin"));
            System.out.println("=============================");
            //  基于多角色的权限控制
            System.out.println(subject.hasAllRoles(Arrays.asList("admin","user")));
            System.out.println(subject.hasAllRoles(Arrays.asList("admin","manager")));
            System.out.println("=============================");
            //  是否具有其中一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin", "user", "manager"));
            for (boolean aBoolean : booleans) {
                System.out.println(aBoolean);
            }
            System.out.println("====这是一个分隔符====");

            //  基于权限字符串的访问控制  用户具有的权限
            System.out.println("权限："+subject.isPermitted("user:update:01"));
            System.out.println("权限："+subject.isPermitted("product:update:02"));
            System.out.println("=============================");

            //  分别具有哪些权限
            boolean[] permitted = subject.isPermitted("user:*:01", "user:update:02");
            for (boolean b : permitted) {
                System.out.println(b);
            }
            System.out.println("=============================");

            //  同时具有哪些权限
            boolean permittedAll = subject.isPermittedAll("product:*:01", "product:update:03");
            System.out.println(permittedAll);

        }

    }
}
