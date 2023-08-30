package com.sesac.reuse.emailverification.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface MailServiceInter {

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;

    public String createKey();

    public String sendSimpleMessage(String to) throws Exception; //메일 발송

}
