package com.sesac.reuse.controller;

import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.ItemRepository;
import com.sesac.reuse.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

// 검색 페이지 제공용 컨트롤러
@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search")
    public String searchItem(@RequestParam("itemName") String itemName, Model model) {

        List<Item> foundItems = itemRepository.findByItemNameContaining(itemName);

        if (foundItems.isEmpty()) {
            return "search_fail";  // 검색 결과가 없는 경우
        }

        model.addAttribute("items", foundItems);
        return "search_success";  // 검색 결과를 보여주는 페이지
    }

    @GetMapping("/item-detail")
    public String itemDetail(@RequestParam("item_id") String itemId, Model model) {
        Item foundItem = itemRepository.findById(itemId).orElse(null);

        if (foundItem == null) {
            return "search_fail";  // 검색 실패 페이지로 이동
        }

        model.addAttribute("item", foundItem);
        return "search_detail";  // 검색 상세 페이지로 이동
    }


//        Item foundItem = itemRepository.findByItemNameContaining(itemName);
//
//        if (foundItem == null) {
//            return "search_fail";  // HTML 템플릿 이름
//        }
//
//        model.addAttribute("item", foundItem);
//        return "search_success";  // HTML 템플릿 이름
//    }


//        Item foundItem = itemRepository.findByItemNameContaining(itemName);

//        if (foundItem == null) {
//            return "search_fail";  // HTML 템플릿 이름
//        }
//
//        model.addAttribute("item", foundItem);
//        return "search_success";  // HTML 템플릿 이름
//    }

    @GetMapping("/게시판")
    public String 게시판() {
        return "게시판";
    }

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