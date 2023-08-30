package com.sesac.reuse.controller;

import com.sesac.reuse.domain.Item;
import com.sesac.reuse.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 검색 페이지 제공용 컨트롤러
@Controller
public class SearchController {

    @Autowired
    private SearchRepository searchRepository;

    @GetMapping("/search")
    public String searchItem(@RequestParam("itemName") String itemName, Model model) {
        Item foundItem = searchRepository.findByItemNameContaining("A" + itemName);
        if (foundItem == null) {
            return "search_fail";  // HTML 템플릿 이름
        }

        model.addAttribute("item", foundItem);
        return "search_success";  // HTML 템플릿 이름
    }

    @GetMapping("/search_success")
    public String searchSuccess() {
        return "search_success";
    }

    @GetMapping("/search_fail")
    public String searchFail() {
        return "search_fail";
    }

    @GetMapping("/게시판")
    public String 게시판() {
        return "게시판";
    }

}
