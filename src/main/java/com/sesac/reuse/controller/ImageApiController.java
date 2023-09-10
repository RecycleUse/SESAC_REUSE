package com.sesac.reuse.controller;

import com.sesac.reuse.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageApiController {

    private final ItemRepository itemRepository;

    @Autowired
    public ImageApiController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/getItemImagePath")
    public ResponseEntity<String> getItemImagePath(@RequestParam("itemId") String itemId) {
        String imagePath = itemRepository.findImageByItemId(itemId);
        return ResponseEntity.ok(imagePath);
    }
}
