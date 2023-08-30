package com.sesac.reuse.controller;

import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 검색 페이지 제공용 컨트롤러
@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search")
    public String searchItem(@RequestParam("itemName") String itemName, Model model) {
        Item foundItem = itemRepository.findByItemNameContaining(itemName);

        if (foundItem == null) {
            return "search_fail";  // HTML 템플릿 이름
        }

        model.addAttribute("item", foundItem);
        return "search_success";  // HTML 템플릿 이름
    }

//    @GetMapping("/search_success")
//    public String searchSuccess() {
//        return "search_success";
//    }
//
//    @GetMapping("/search_fail")
//    public String searchFail() {
//        return "search_fail";
//    }

    @GetMapping("/게시판")
    public String 게시판() {
        return "게시판";
    }

}
