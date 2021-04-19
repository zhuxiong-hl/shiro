package com.xxx.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 */
public class CustomerRealm extends AuthorizingRealm {
    //  授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("=============================================");
        return null;
    }
    //  认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //  从token中获取用户名
        String principal = (String) token.getPrincipal();
        System.out.println(principal);

        //假设username,password是从数据库获得的信息
        String username="lisi";
        String password="123456";
        if (username.equals(principal)){
            /**
             * principal: 返回数据库中正确的用户名
             * password： 返回数据库中正确的密码
             * this.getName(): 提供当前realm的名字
             */
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, password, this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
