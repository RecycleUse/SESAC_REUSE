package com.sesac.reuse.emailverification.controller;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class AccountControllerTest {

    @Autowired
    private AccountController accountController;

    @Test
    void changePwdTest() throws Exception {
        //given
        HashMap<String, String> payLoad = new HashMap<>();
        payLoad.put("email", "test@naver.com");

        //when
        accountController.resetPwd(payLoad);

        //then
        Assertions.assertThat(HttpStatus.OK);

    }

}