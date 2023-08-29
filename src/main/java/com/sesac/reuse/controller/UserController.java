package com.sesac.reuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    // 시큐리티 default login페이지를 안쓰고 커스텀 쓰는경우에는 GET요청 Controller 생성해줘야함
    @GetMapping("/user/login")
    public String loginPage() {
        return "/user/login";
    }

    //시큐리티 기본 제공 user로 로그인 테스트하면 "/login?error로 리다이렉트되고 SecurityContext 에 저장안됐다고 나오는데
    //익명 사용자(시큐리티 제공 user)라 세션(SecurityContext)에 저장 안하는 최적화임.
    //저장되는거 확인하고싶다면, 메모리 유저 or 테스트코드로
    @PostMapping("/user/login")
    public String login() {
        return "ok";
    }
}
