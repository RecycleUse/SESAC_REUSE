package com.sesac.reuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
@EnableJpaAuditing // AuditingEntityListener 활성화
@SpringBootApplication
public class ReuseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReuseApplication.class, args);
	}

}
