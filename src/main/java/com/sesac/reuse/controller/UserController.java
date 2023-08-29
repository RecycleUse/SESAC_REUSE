package com.sesac.reuse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

// Login 컨트롤러
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    private final OAuth2AuthorizedClientService authorizedClientService;
//
//    public UserController(OAuth2AuthorizedClientService authorizedClientService) {
//        this.authorizedClientService = authorizedClientService;
//    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, OAuth2AuthenticationToken authentication) {
//        OAuth2AccessToken accessToken = authorizedClientService.loadAuthorizedClient(
//            authentication.getAuthorizedClientRegistrationId(),
//            authentication.getName()
//        ).getAccessToken();
//
//        // 카카오 로그아웃 API 호출
//        String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());
//        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.POST, URI.create(kakaoLogoutUrl));
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.exchange(requestEntity, Void.class);
//
//        return "redirect:/";
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

}
