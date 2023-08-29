package com.sesac.reuse.config;

import com.sesac.reuse.user.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  // 해당 클래스를 스프링 설정 클래스로 등록
@EnableWebSecurity  // Web 보안 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired  // 해당 타입의 빈을 자동으로 주입
    private CustomOAuth2UserService customOAuth2UserService;

    @Override  // 상위 클래스의 메소드를 오버라이드
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()  // HttpServletRequest에 따라 접근을 제한하거나 허용하려면 사용
                .antMatchers("/h2-console/**").permitAll()  // H2 데이터베이스 콘솔에 대한 모든 요청을 허용
                .anyRequest().authenticated()  // 그 외의 모든 요청은 인증이 필요
                .and()
                .oauth2Login()  // OAuth 2 로그인 기능 활성화
                .userInfoEndpoint()  // OAuth 2 사용자 정보를 얻기 위한 설정
                .userService(customOAuth2UserService)  // OAuth 2 사용자 정보를 얻기 위한 서비스 지정
                .and()
                .defaultSuccessUrl("/success")  // 로그인 성공 시 리다이렉트할 URL
                .failureUrl("/login?error=true")  // 로그인 실패 시 리다이렉트할 URL
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")  // CSRF 보호에서 H2 콘솔 URL 제외
                .and()
                .headers().frameOptions().disable();  // X-Frame-Options 헤더를 비활성화하여 iframe 내에서 페이지를 렌더링 허용
    }
}
