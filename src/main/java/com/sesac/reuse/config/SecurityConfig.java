package com.sesac.reuse.config;

import com.sesac.reuse.user.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpSession;

import static org.hibernate.criterion.Restrictions.and;

@Configuration  // 해당 클래스를 스프링 설정 클래스로 등록
@EnableWebSecurity  // spring security 설정을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired  // 해당 타입의 빈을 자동으로 주입
    private CustomOAuth2UserService customOAuth2UserService;

    @Override  // 상위 클래스의 메소드를 오버라이드
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 카카오 로그인
                .authorizeRequests()  // HttpServletRequest에 따라 접근을 제한하거나 허용하려면 사용
                .antMatchers("/", "/user/**", "/search_fail", "/게시판").permitAll()  // 해당 경로에 대한 모든 요청을 허용
                .antMatchers( "/static1/**", "/static2/**").permitAll() // 정적 자원에 대한 접근 허용
                .anyRequest().authenticated()  // 그 외의 모든 요청은 인증이 필요
                .and()
                .oauth2Login()  // OAuth 2 로그인 기능 활성화
                .userInfoEndpoint()  // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장
                .userService(customOAuth2UserService)  // OAuth 2 사용자 정보를 얻기 위한 서비스 지정
                .and()
                .defaultSuccessUrl("/")  // 로그인 성공 시 리다이렉트할 URL
                .failureUrl("/login?error=true")  // 로그인 실패 시 리다이렉트할 URL
                .and()
                .csrf().ignoringAntMatchers("/", "/user/**", "/search_fail", "/게시판")  // CSRF 보호에서 제외 페이지
                .and()
                .headers().frameOptions().disable()  // X-Frame-Options 헤더를 비활성화하여 iframe 내에서 페이지를 렌더링 허용
                .and()

                // 카카오 로그아웃
                .logout()
                .logoutUrl("/user/logout")  // 로그아웃을 수행하는 URL
                .logoutSuccessUrl("/")  // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .addLogoutHandler((request, response, authentication) -> {  // 소셜 로그아웃 처리 및 세션 관련 작업 등 추가
                    HttpSession session = request.getSession();  // 세션을 무효화하여 사용자의 세션 정보를 제거
                    session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> response.sendRedirect("/user/login"))  // 소셜 로그아웃 성공 후 처리 및 리다이렉트3
                .deleteCookies("remember-me");  // 로그아웃 시 제거할 쿠키를 설정
    }
}



//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Log4j2
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    private final DataSource dataSource;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        log.info("------------configure-------------------");
//
//        //커스텀 로그인 페이지
//        http.formLogin().loginPage("/user/login");
//        //CSRF 토큰 비활성화
//        http.csrf().disable();
//
//        http.oauth2Login().loginPage("/user/login");
//
//
//        return http.build();
//    }
//}
