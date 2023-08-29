package com.sesac.reuse.controller;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@Log4j2
class MemberControllerTest {

    @Autowired
    private MemberController memberController;

    @WithMockUser(username="testUser", password = "1234")
    @Test
    void signup() {
        //given
//        memberController.login();

        //then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user={}",authentication.getPrincipal().toString());
    }

}