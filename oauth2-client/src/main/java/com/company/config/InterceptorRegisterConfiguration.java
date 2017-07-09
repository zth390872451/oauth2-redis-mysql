package com.company.config;

import com.company.filter.Oauth2Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc //开启spring mvc的相关默认配置
public class InterceptorRegisterConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Oauth2Interceptor())
    	.excludePathPatterns("/api/oauth2/**");//添加Oauth2Interceptor，除了/api/oauth2/**下的接口都需要进行 AccessToken 的校验
    }
}
