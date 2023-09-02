package com.sesac.reuse.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// 시큐리티 5,4부터 filterChain 빈으로 구현 가능, Adapter 구현 방식 deprecated
@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true) //@PreAuthorize 메서드 수준 제어 활성화
public class WebSecurityConfig {

    /*
    SecurityFilterChain 인터페이스
        - HTTP요청에 대한 보안 구성 제공
        - HttpSecurity 객체를 통해 각종 보안 규칙(인증 방식, 접근 제어 규칙 등)을 세부적으로 설정 가능

     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) //csrf 보호 기능 해제, (시큐리티는 GET 제외 모든 요청을 CSRF 체크가 default)
                .authorizeHttpRequests(authz -> authz
                        .mvcMatchers("/","/index","/home").permitAll() // 해당 경로에 대한 모든 요청(get,put,post,delete) 다 처리 권한없이 허용
                        .mvcMatchers("/user/login","/signup/mailConfirm").permitAll()
                        .mvcMatchers("/reset-pwd").permitAll()
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                )
                .httpBasic(withDefaults());

        http
                .formLogin(form -> form
                        .loginPage("/member/login") // 시큐리티 default login페이지를 안쓰고 커스텀 쓰는경우에는 GET요청 Controller 생성해줘야함
                        .usernameParameter("email")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/member/profile")
                        .permitAll()
                ); //시큐리티의 경우 filter에서 요청받고 내부적으로 controller구성 , 로그인,로그아웃 Controller는 직접 생성 안해도됨


        return http.build();
    }

    /*
    WebSecurityCustomizer 인터페이스
        - Spring Security의 WebSecurity 객체를 구성하기 위해 사용
        - WebSecurity 는 Spring Security 필터 체인에 대한 보안 설정 제공
        - 특정 요청에 대한 보안 필터 적용을 무시, 기본적으로 적용되는 보안 필터 순서 변경 등 설정 가능
    * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/static/**"); //ant패턴 (부정 의미 아님 ㅋㅋ)
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /*
    SecurityFilterChain 와 WebSecurityCustomizer 는 무슨 차이일까
    - WebSecurityCustomizer 는 전반적인 웹 보안 설정에 초점
    - SecurityFilterChain는 HTTP 요청 단위의 세부적인 보안 정책에 초점
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
