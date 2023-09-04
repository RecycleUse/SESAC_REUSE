package com.sesac.reuse.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class AdminController {

    //권한 허용 테스트용
    @GetMapping("/admin/test")
    public String test() {
        return "/admin/test";
    }
}
