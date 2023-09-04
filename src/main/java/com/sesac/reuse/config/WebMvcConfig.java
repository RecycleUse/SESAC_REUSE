package com.sesac.reuse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**") // 정적 리소스에 접근할 경로를 지정
                .addResourceLocations("classpath:/static/"); // 클래스패스 내의 정적 리소스 디렉토리를 지정
    }
}