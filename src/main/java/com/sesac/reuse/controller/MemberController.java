package com.sesac.reuse.controller;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;
import com.sesac.reuse.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    // 시큐리티 default login페이지를 안쓰고 커스텀 쓰는경우에는 GET요청 Controller 생성해줘야함
    @GetMapping("/member/login")
    public String loginPage() {
        return "/member/login";
    }

    //시큐리티 기본 제공 user로 로그인 테스트하면 "/login?error로 리다이렉트되고 SecurityContext 에 저장안됐다고 나오는데
    //익명 사용자(시큐리티 제공 user)라 세션(SecurityContext)에 저장 안하는 최적화임.
    //저장되는거 확인하고싶다면, 메모리 유저 or 테스트코드로


    @GetMapping("/member/signup")
    public String signUpPage() {
        return "/member/signup";
    }

    @PostMapping("/member/signup")
    public String signUp( MemberDTO memberDTO) {
        log.info("memberDTO={}",memberDTO);

        try {
            memberService.join(memberDTO);
        }catch (EmailExistException e) {
            log.error("이미 존재하는 회원입니다."); // 프론트단으로 에러보내주기
        }

        return "redirect:/member/login";
    }

    @GetMapping("/member/myPage")
    public String myPage() {
        return "/member/my_page";
    }
}
