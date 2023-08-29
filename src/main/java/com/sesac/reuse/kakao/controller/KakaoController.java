package com.sesac.reuse.kakao.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/kakao")
    public RedirectView kakaoLogin() {
        return new RedirectView("/oauth2/authorization/kakao");
    }
    @GetMapping("/success")
    public String success() {
        return "success";
    }

}
