package com.sesac.reuse.emailverification.service;

import com.sesac.reuse.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
@Log4j2
public class ResetPwdMailService implements MailServiceInter{

    private final JavaMailSender mailSender;
    private final MemberService memberService;
    private String tempPw;

    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {

        log.info("Recipient={}", to);
        log.info("Verification Code={}", tempPw);

        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); //수신자
        message.setSubject("REUSE 임시 비밀번호 발급 메일입니다.");

        StringBuffer msg = new StringBuffer();
        msg.append("<div style='margin:100px;'>");
        msg.append("<h1> 안녕하세요</h1>");
        msg.append("<h1> REUSE 입니다</h1>");
        msg.append("<br>");
        msg.append("<p>아래 임시 비밀번호로 로그인 후 비밀번호를 재설정해주십시오.<p>");
        msg.append("<br>");
        msg.append("<p> 감사합니다!<p>");
        msg.append("<br>");
        msg.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        msg.append("<h3 style='color:blue;'>임시 비밀번호입니다.</h3>");
        msg.append("<div style='font-size:130%'>");
        msg.append("CODE : <strong>").append(tempPw).append("</strong><div><br/> ");


        message.setText(msg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("the_blue10@naver.com", "REUSE_ADMIN"));

        return message; //내용, 수신인, 발신인 설정
    }



    @Override
    public String sendSimpleMessage(String to) throws Exception {

        tempPw = createKey(); //임시 비번

        memberService.resetPwd(to, tempPw);

        MimeMessage message = createMessage(to);

        try {
            mailSender.send(message);
        }catch (MailException e) {
            log.error(e);
            throw new IllegalStateException();
        }

        return "changePw"; //여긴 실시간으로 일치하는지 확인할 필요는 없다보니..
    }
}
