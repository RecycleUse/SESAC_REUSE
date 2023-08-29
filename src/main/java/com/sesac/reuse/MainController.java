package com.sesac.reuse;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MainController {

    @GetMapping("/hello")
    public void main() {
        log.info("호출");
    }
}