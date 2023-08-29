package com.sesac.reuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 검색 페이지 제공용 컨트롤러
@Controller
public class SearchController {

    @GetMapping("/search_fail")
    public String searchFail() {
        return "search_fail";
    }

    @GetMapping("/게시판")
    public String 게시판() {
        return "게시판";
    }

}
