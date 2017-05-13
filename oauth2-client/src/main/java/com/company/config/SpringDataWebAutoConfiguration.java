package com.company.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * 默认参数配置
 * @author Administrator
 *
 */
@Configuration
@EnableSpringDataWebSupport
@ConditionalOnWebApplication
@ConditionalOnClass(PageableHandlerMethodArgumentResolver.class)//分页默认参数
@ConditionalOnMissingBean(PageableHandlerMethodArgumentResolver.class)
@AutoConfigureAfter(RepositoryRestMvcAutoConfiguration.class)
public class SpringDataWebAutoConfiguration {

}
