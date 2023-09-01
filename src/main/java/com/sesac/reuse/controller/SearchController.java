package com.sesac.reuse.controller;

import com.sesac.reuse.dto.ItemWithImagesDTO;
import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.ItemRepository;
import com.sesac.reuse.repository.ImageRepository;
import com.sesac.reuse.service.SearchService;
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

    private final ItemRepository itemRepository;
    private final SearchService searchService;  // 추가

    @Autowired
    public SearchController(ItemRepository itemRepository, SearchService searchService) {  // 수정
        this.itemRepository = itemRepository;
        this.searchService = searchService;
    }
    @GetMapping("/search")
    @ResponseBody
    public List<Item> searchItem(@RequestParam("itemName") String itemName) {
        return itemRepository.findByItemNameContaining(itemName);
    }

    @GetMapping("/search-success")
    public String searchSuccess(@RequestParam("itemName") String itemName, Model model) {
        List<Item> foundItems = itemRepository.findByItemNameContaining(itemName);
        model.addAttribute("items", foundItems);
        return "search-success";
    }

    @GetMapping("/item-detail")
    public String itemDetail(@RequestParam("item_id") String itemId, Model model) {
        ItemWithImagesDTO itemWithImages = searchService.getItem(itemId);  // 변경

        if (itemWithImages.getItem() == null) {
            return "search-fail";
        }

        model.addAttribute("item", itemWithImages.getItem());
        model.addAttribute("images", itemWithImages.getImages());  // 이미지 정보 추가
        return "search-detail";
    }

    @GetMapping("/search-fail")
    public String searchFail() {
        return "search-fail";
    }

    @GetMapping("/게시판")
    public String 게시판() {
        return "게시판";
    }
}

//    @GetMapping("/item-detail")
//    public String itemDetail(@RequestParam("item_id") String itemId, Model model) {
//        ItemWithImagesDTO itemWithImages = searchService.getItem(itemId);  // 변경
//
//        if (itemWithImages.getItem() == null) {
//            return "search-fail";
//        }
//
//        model.addAttribute("item", itemWithImages.getItem());
//        model.addAttribute("images", itemWithImages.getImages());  // 이미지 정보 추가
//        return "search-detail";
//    }
