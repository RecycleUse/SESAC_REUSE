package com.sesac.reuse.security.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CustomMemberDetailsServiceTest {

    @Autowired
    private CustomMemberDetailsService memberDetailsService;


}