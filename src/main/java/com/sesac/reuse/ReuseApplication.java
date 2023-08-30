package com.sesac.reuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize 등 사용하기 위해서 필요
@SpringBootApplication
public class ReuseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReuseApplication.class, args);
	}

}
