package com.sesac.reuse.controller;

import com.sesac.reuse.service.Like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likeItem")
    public ResponseEntity<String> likeItem(@RequestParam String itemId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String userEmail = userDetails.getUsername(); // 사용자 이메일 가져오기
            try {
                likeService.likeItem(userEmail, itemId);
                return ResponseEntity.ok("좋아요 추가 성공");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("좋아요 추가 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자");
        }
    }

    @PostMapping("/unlikeItem")
    public ResponseEntity<String> unlikeItem(@RequestParam String itemId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String userEmail = userDetails.getUsername(); // 사용자 이메일 가져오기
            try {
                likeService.unlikeItem(userEmail, itemId);
                return ResponseEntity.ok("좋아요 제거 성공");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("좋아요 제거 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자");
        }
    }
}