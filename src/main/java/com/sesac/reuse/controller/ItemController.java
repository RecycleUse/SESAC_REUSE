package com.sesac.reuse.controller;

import com.sesac.reuse.dto.item.ItemDTO;
import com.sesac.reuse.entity.item.Category;
import com.sesac.reuse.entity.item.Item;
import com.sesac.reuse.service.itemAdmin.CategoryService;
import com.sesac.reuse.service.itemAdmin.ItemCustomIdSevice;
import com.sesac.reuse.service.itemAdmin.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemCustomIdSevice itemCustomIdSevice;

    // 아이템 목록
    @GetMapping("")
    public String getItemList(Model model, @RequestParam(value="page", defaultValue="0") int page){
        Page<Item> itemPaging = itemService.getList(page);
        model.addAttribute("itemPaging", itemPaging);
        return "adminitem/itemList";
    }

    // 아이템 조회
    @GetMapping("/detail/{itemId}")
    public String getItem(@PathVariable("itemId") String itemId, Model model){
        Item itemDetail = itemService.getItem(itemId);
        model.addAttribute("itemDetail", itemDetail);
        return "adminitem/itemDetail";
    }

    // 아이템 삭제
    @GetMapping("/remove/{itemId}")
    public String deleteItem(@PathVariable("itemId") String itemId){
        itemService.deleteItem(itemId);
        return "redirect:/item";
    }

    // 아이템 추가
    @GetMapping("/register")
    public String addItem(Model model) {
        List<Category> categories = categoryService.getCategoryList();
        Map<String, String> itemCustomIdList = itemCustomIdSevice.getItemCustomIdList();
        model.addAttribute("categories", categories);
        model.addAttribute("itemCustomIdList", itemCustomIdList);
        return "adminitem/addItem";
    }

    @PostMapping("/register")
    public String addItem(@ModelAttribute ItemDTO itemDto) throws IOException {
        System.out.println("itemDto = " + itemDto);
        itemService.saveItem(itemDto);
        //itemService.createItem(item);
        return "redirect:/item";
    }

    // 아이템 수정 페이지 이동
    @GetMapping("/update/{itemId}")
    public String updateItem(@PathVariable("itemId") String itemId, Model model){
        Item itemDetail = itemService.getItem(itemId);
        List<Category> categories = categoryService.getCategoryList();
        Map<String, String> itemCustomIdList = itemCustomIdSevice.getItemCustomIdList();
        model.addAttribute("categories", categories);
        model.addAttribute("itemCustomIdList", itemCustomIdList);
        model.addAttribute("itemDetail", itemDetail);
        return "adminitem/itemUpdate";
    }

    // 아이템 수정
    @PostMapping("/update/{itemId}")
    public String updateItem(@PathVariable("itemId") String itemId, ItemDTO updateItem) throws IOException {
        updateItem.setCreatedAt(LocalDateTime.now());

        if(!itemId.equals(updateItem.getId())) { // 기존 item_id 와 다르면 삭제하고 다시 추가
            itemService.deleteItem(itemId);
            itemService.saveItem(updateItem);
            return "redirect:/item";
        } else { // 기존 item_id 와 같으면 수정
            Item item = itemService.updateItem(updateItem);
            return "redirect:/item/detail/" + itemId;
        }

    }

    @PostMapping
    public ResponseEntity<Void> uploadItem(@ModelAttribute ItemDTO itemDto) throws IOException {
        itemService.saveItem(itemDto);
        return ResponseEntity.ok().build();
    }


}
