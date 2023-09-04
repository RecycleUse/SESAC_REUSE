package com.sesac.reuse.controller;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.repository.itemSearch.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

// 검색 페이지 제공용 컨트롤러
@Controller
public class SearchController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/search")
    @ResponseBody  // JSON 형식으로 응답을 반환함을 선언 (검색 드롭다운 기능에 사용)
    public List<Item> searchItem(@RequestParam("itemName") String itemName) {
        List<Item> foundItems = itemRepository.findByItemNameContaining(itemName);

        return foundItems;
    }


    @GetMapping("/search-success")
    public String searchSuccess(@RequestParam("itemName") String itemName, Model model) {
        List<Item> foundItems = itemRepository.findByItemNameContaining(itemName);
        model.addAttribute("items", foundItems);
        return "item/search-success"; // 검색 결과 페이지로 이동
    }

    @GetMapping("/item-detail")
    public String itemDetail(@RequestParam("item_id") String itemId, Model model) {
        Item foundItem = itemRepository.findById(itemId).orElse(null);

        if (foundItem == null) {
            return "item/search-fail";  // 검색 실패 페이지로 이동
        }

        model.addAttribute("item", foundItem);
        return "item/search-detail";  // 검색 상세 페이지로 이동
    }

    @GetMapping("/search-fail")
    public String searchFail() {
        return "item/search-fail"; // 검색 실패 페이지로 이동
    }

}


//    @GetMapping("/search")
//    public String searchItem(@RequestParam("item_name") String item_name, Model model) {
//        List<Item> foundItems = itemRepository.findByitem_nameContaining(item_name);
//
//        if (foundItems.isEmpty()) {
//            return "search-fail";
//        } else if (foundItems.size() == 1) {
//            // 검색 결과가 한 개인 경우, 해당 아이템의 상세 페이지로 바로 이동
//            model.addAttribute("item", foundItems.get(0));
//            return "search-detail";
//        } else {
//            // 검색 결과가 여러 개인 경우, 검색 결과 페이지로 이동
//            model.addAttribute("items", foundItems);
//            return "search-success";
//        }
//    }