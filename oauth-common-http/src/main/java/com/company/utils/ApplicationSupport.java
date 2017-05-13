package com.company.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Administrator on 2017/5/8.
 */
@Component
public class ApplicationSupport implements DisposableBean, ApplicationContextAware {

    private static ApplicationContext applicationContext;
    // 获取配置文件参数值
    public static String getParamVal(String paramKey){
        return applicationContext.getEnvironment().getProperty(paramKey);
    }

    // 获取bean对象
    public static Object getBean(String name) {
        Assert.hasText(name);
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
   public void destroy() throws Exception {
        applicationContext = null;
    }

}
