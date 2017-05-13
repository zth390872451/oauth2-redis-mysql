package com.company.config;

import com.company.filter.Oauth2Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class InterceptorRegisterConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //match .json api version
		registry.addInterceptor(new Oauth2Interceptor())
    	.excludePathPatterns("/api/oauth2/**","/api/member/regist*","/api/member/create","/api/member/upass*","/api/common/msg",
    			"/api/content/**","/api/common/**","/api/albums/**","/api/common/isExistAccessToken","/api/device/**","/api/video/ackJoinChannel",
    			"/api/about/gotoPage","/api/charge/**",
    			"/api/holderStep/step","/api/test/*","/api/sport/info","/monitor/ping","/api/receipt/receiptResult","/api/paipai/**","/api/thirdParty/**","/api/testController/**");

    }
	
	//静态资源路径配置
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/resources/");
    }
}
