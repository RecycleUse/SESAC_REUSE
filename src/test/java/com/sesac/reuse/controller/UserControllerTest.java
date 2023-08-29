package com.sesac.reuse.controller;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class UserControllerTest {

    @Autowired
    private UserController userController;

    @WithMockUser(username="testUser", password = "1234")
    @Test
    void signup() {
        //given
        userController.login();

        //then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user={}",authentication.getPrincipal().toString());
    }

}