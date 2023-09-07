package com.sesac.reuse.controller;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.itemSearch.ItemRepository;
import com.sesac.reuse.service.Like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/like")
public class LikeController {

    private final ItemRepository itemRepository;
    private final LikeService likeService;

    @Autowired
    public LikeController(ItemRepository itemRepository, LikeService likeService) {
        this.itemRepository = itemRepository;
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeItem(@RequestParam("item_id") String itemId, @RequestParam("user_id") Long userId) {
        try {
            // 아이템 ID를 사용하여 해당 아이템을 데이터베이스에서 조회
            Item item = itemRepository.findById(itemId).orElse(null);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이템을 찾을 수 없음");
            }

            likeService.likeItem(userId, item); // userId와 Item 전달
            return ResponseEntity.ok("좋아요 추가 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 추가 실패");
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<String> unlikeItem(@RequestParam("item_id") String itemId, @RequestParam("user_id") Long userId) {
        try {
            // 아이템 ID를 사용하여 해당 아이템을 데이터베이스에서 조회
            Item item = itemRepository.findById(itemId).orElse(null);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이템을 찾을 수 없음");
            }

            likeService.unlikeItem(userId, item);
            return ResponseEntity.ok("좋아요 제거 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 제거 실패");
        }
    }
}