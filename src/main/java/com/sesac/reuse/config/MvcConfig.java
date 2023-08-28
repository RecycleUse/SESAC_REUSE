package com.sesac.reuse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index"); //setViewName : viewName 이라서 "/index" 이렇게 경로 ㄴㄴ
        registry.addViewController("/home").setViewName("index");
    }
}
