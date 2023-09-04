package com.sesac.reuse.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Login 컨트롤러

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // 현재 인증 객체(Authentication)를 가져옴
        if (authentication != null) {  // 가져온 인증 객체를 이용하여 SecurityContextLogoutHandler를 통해 로그아웃 실행
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/singup")
    public String join() {
        return "singup";
    }

//    private final OAuth2AuthorizedClientService authorizedClientService;
//
//    public UserController(OAuth2AuthorizedClientService authorizedClientService) {
//        this.authorizedClientService = authorizedClientService;
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, OAuth2AuthenticationToken authentication) {  // 현재 사용자의 OAuth 2.0 인증 정보를 가져옴
//        OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
//            authentication.getAuthorizedClientRegistrationId(),
//            authentication.getName()
//        ).getAccessToken();
//
//        // 가져온 OAuth 2.0 인증 정보를 이용하여 카카오 로그아웃 API를 호출
//        String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());
//        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.POST, URI.create(kakaoLogoutUrl));
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.exchange(requestEntity, Void.class);
//
//        return "redirect:/";  // 호출 결과와 관계없이 "/"로 리다이렉트
//    }

}
