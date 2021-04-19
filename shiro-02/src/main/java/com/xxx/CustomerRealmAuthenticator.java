package com.xxx;


import com.xxx.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 测试自定义Realm
 */
public class CustomerRealmAuthenticator {
    public static void main(String[] args) {
        //  创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //  给安全管理器设置realm （设置为自定义realm获取认证数据）
        securityManager.setRealm(new CustomerRealm());
        //  给安全工具类中设置默认安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //  获取主体对象subject
        Subject subject = SecurityUtils.getSubject();
        //  创建令牌token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        try {
            subject.login(token);
            System.out.println("登陆成功~~~");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败，密码错误");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败，用户名不存在");
        }
    }
}
