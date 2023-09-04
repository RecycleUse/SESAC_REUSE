package com.sesac.reuse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index"); //setViewName : viewName 이라서 "/index" 이렇게 경로 ㄴㄴ
        registry.addViewController("/home").setViewName("index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**") // 정적 리소스에 접근할 경로를 지정
                .addResourceLocations("classpath:/static/"); // 클래스패스 내의 정적 리소스 디렉토리를 지정
    }
}

