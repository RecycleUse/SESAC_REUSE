package com.sesac.reuse.emailverification.controller;


import com.sesac.reuse.emailverification.service.RegisterMailService;
import com.sesac.reuse.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AccountController {

//view에서 사용자가 메인 인증 버튼 클릭 -> mailConfirm 호출 ->
// 요청에 넘어온 메일로 인증 코드 넘김 -> 인증번호가 view로 리턴

    private final RegisterMailService registerMailService;
    private final MemberService memberService;


    @PostMapping("/signup/mailConfirm")
    public ResponseEntity<?> mailConfirm(@RequestBody Map<String,String> payload,
                                         RedirectAttributes redirectAttributes) throws Exception {

        log.info("payload={}",payload);
        String email = payload.get("email");

        //존재하는 회원이면 가입된 회원이라고 알려주기
        if(memberService.isExistAccount(email)) {
//            redirectAttributes.addFlashAttribute("error","email");
//            return "redirect:/member/signup";
            return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        String vertificationCode = registerMailService.sendSimpleMessage(email);// param email로 메시지를 보낼거야
        log.info("vertificationCode={}",vertificationCode);
        return new ResponseEntity<>(vertificationCode, HttpStatus.OK); //서버에 저장해야됨
    }
}
