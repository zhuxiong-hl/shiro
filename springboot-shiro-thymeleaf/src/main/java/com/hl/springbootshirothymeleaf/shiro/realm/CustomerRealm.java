package com.hl.springbootshirothymeleaf.shiro.realm;

import com.hl.springbootshirothymeleaf.entity.Perm;
import com.hl.springbootshirothymeleaf.entity.User;
import com.hl.springbootshirothymeleaf.service.UserService;
import com.hl.springbootshirothymeleaf.shiro.salt.MyByteSource;
import com.hl.springbootshirothymeleaf.utils.ApplicationContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findRolesByUserName(primaryPrincipal);
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        String principal = (String) authenticationToken.getPrincipal();
        User user = userService.findByUserName(principal);
        if (!ObjectUtils.isEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
