package com.sesac.reuse.emailverification.service;

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
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegisterMailService implements MailServiceInter {

    private final JavaMailSender mailSender;
    public String ePw; //인증 번호

    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {

        log.info("Recipient={}", to);
        log.info("Verification Code={}", ePw);

        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); //수신자
        message.setSubject("REUSE 회원가입 이메일 인증 메일입니다.");

        StringBuffer msg = new StringBuffer();
        msg.append("<div style='margin:100px;'>");
        msg.append("<h1> 안녕하세요</h1>");
        msg.append("<h1> REUSE 입니다</h1>");
        msg.append("<br>");
        msg.append("<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>");
        msg.append("<br>");
        msg.append("<p> 감사합니다!<p>");
        msg.append("<br>");
        msg.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        msg.append("<h3 style='color:#00814F;'>회원가입 인증 코드입니다.</h3>");
        msg.append("<div style='font-size:130%'>");
        msg.append("CODE : <strong style='#00814F';>").append(ePw).append("</strong><div><br/> ");


        message.setText(msg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("the_blue10@naver.com", "REUSE_ADMIN"));

        return message; //내용, 수신인, 발신인 설정
    }


    //메일 발송
    //MimeMessage 객체 안에 message 내용 담기
    @Override
    public String sendSimpleMessage(String to) throws Exception {

        ePw = createKey(); //랜덤 인증번호 생성

        MimeMessage message = createMessage(to); //메일 내용 생성, 수신인, 발신인

        try {
            mailSender.send(message);
        } catch (MailException e) {
            log.error(e);
            throw new IllegalStateException(); //<--굳이?
        }

       return ePw; //서버에도 저장 후 일치확인하기 위해 서버로 return
    }
}
