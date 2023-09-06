package com.sesac.reuse.emailverification.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//JvaMailSender 등록

@Configuration
@Log4j2
public class MailConfig {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        log.info("mailUsername={}",mailUsername);
        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);

        javaMailSender.setPort(465);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
        properties.setProperty("mail.smtp.auth", "true"); //1 smtp 인증
        properties.setProperty("mail.smtp.starttls.enable", "true"); //1 smtp strattles 사용
        properties.setProperty("mail.debug", "true"); // 디버그 사용
        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com"); //1 ssl 인증 서버는 smtp.naver.com
        properties.setProperty("mail.smtp.ssl.enable","true"); //1 ssl 사용
        return properties;    }

}
