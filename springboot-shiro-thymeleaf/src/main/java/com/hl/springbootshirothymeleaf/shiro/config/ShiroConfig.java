package com.hl.springbootshirothymeleaf.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hl.springbootshirothymeleaf.shiro.cache.RedisCacheManager;
import com.hl.springbootshirothymeleaf.shiro.realm.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.realm.Realm;

import java.util.HashMap;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager webSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        HashMap<String, String> map = new HashMap<>();
        map.put("/user/**","anon");
        map.put("/user/login","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setLoginUrl("/user/logins");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRealm(realm);
        return webSecurityManager;
    }

    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(1024);
        matcher.setHashAlgorithmName("md5");
        customerRealm.setCredentialsMatcher(matcher);

        customerRealm.setCachingEnabled(true);
        customerRealm.setAuthenticationCachingEnabled(true);
        customerRealm.setAuthorizationCachingEnabled(true);
        customerRealm.setAuthenticationCacheName("authenticationCacheName");
        customerRealm.setAuthorizationCacheName("authorizationCacheName");
        customerRealm.setCacheManager(new RedisCacheManager());
        return customerRealm;
    }
}
