package com.sesac.reuse.emailverification.controller;


import com.sesac.reuse.emailverification.service.RegisterMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping
public class AccountController {

//view에서 사용자가 메인 인증 버튼 클릭 -> mailConfirm 호출 ->
// 요청에 넘어온 메일로 인증 코드 넘김 -> 인증번호가 view로 리턴

    private final RegisterMailService registerMailService;

    //127.0.0.1:8080/ROOT/api/mail/confirm.json?email
//    @PostMapping("/signup/mailConfirm")
//    public String mailConfirm(@RequestParam("email") String email) throws Exception {
//        log.info("나 호출됐니?");
//        log.info("email={}",email);
//
//        String vertificationCode = registerMailService.sendSimpleMessage(email);// param email로 메시지를 보낼거야
//        log.info("vertificationCode={}",vertificationCode);
//
//        return vertificationCode; //서버에 저장해야됨
//    }

    @PostMapping("/signup/mailConfirm")
    public String mailConfirm() throws Exception {
        log.info("나 호출됐니?");
//        log.info("email={}",email);
//
//        String vertificationCode = registerMailService.sendSimpleMessage(email);// param email로 메시지를 보낼거야
//        log.info("vertificationCode={}",vertificationCode);
//
//        return vertificationCode; //서버에 저장해야됨
        return "null";
    }
}
