package com.pjb.springbootjjwt.config;

/**
 * Created by yaocong on 2019/1/23.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 主动设置URL匹配路径
 *
 * @author JustryDeng
 * @date 2018年8月4日 上午1:36:38
 */
@Configuration
public class MyURLPatternConfiguration extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/C语言程序设计/**").addResourceLocations("classpath:/static/C语言程序设计");
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/python爬虫技巧/**").addResourceLocations("classpath:/static/python爬虫技巧");
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/数据结构/**").addResourceLocations("classpath:/static/数据结构");
        super.addResourceHandlers(registry);
    }
}