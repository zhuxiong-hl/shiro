package com.hl.springboot.realm;

import com.hl.springboot.entity.Perm;
import com.hl.springboot.entity.User;
import com.hl.springboot.salt.MySimpleByteSource;
import com.hl.springboot.service.UserService;
import com.hl.springboot.utils.ApplicationContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        User user = userService.findRoleByUserName(primaryPrincipal);
        System.out.println("调用授权验证user: "+user);
        if (!CollectionUtils.isEmpty(user.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                List<Perm> list = userService.findPermByRoleId(role.getId());
                list.forEach(perm -> {
                    simpleAuthorizationInfo.addStringPermission(perm.getPermName());
                });
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        String principal = (String) token.getPrincipal();
        System.out.println("用户名："+principal);
        User user = userService.findByUserName(principal);
        System.out.println("User:"+user);
        if (!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),new MySimpleByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
