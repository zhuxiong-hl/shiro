package com.hl.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextUtil：用于更加灵活地获取IOC容器中组件
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }
}
