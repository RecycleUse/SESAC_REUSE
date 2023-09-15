package com.sesac.reuse.service.Like;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.entity.itemSearch.Like;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.itemSearch.LikeRepository;
import com.sesac.reuse.service.itemSearch.SearchService;
import com.sesac.reuse.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final SearchService searchService;

    @Autowired
    public LikeService(LikeRepository likeRepository, MemberService memberService, SearchService searchService) {
        this.likeRepository = likeRepository;
        this.memberService = memberService;
        this.searchService = searchService;
    }

    public void likeItem(String userEmail, String itemId) {
        // Email을 기반으로 Member 엔터티를 조회
        Member member = memberService.findMemberByEmail(userEmail);

        // itemId를 기반으로 Item 엔터티를 조회
        Item item = searchService.getItemWithImage(itemId);

        if (member != null && item != null) {
            boolean exists = likeRepository.existsByMemberidAndItem(member, item);
            if (!exists) {
                Like like = new Like(member, item);
                likeRepository.save(like);
            }
        } else {
            throw new IllegalArgumentException("Invalid user or item.");
        }
    }


    // 좋아요 제거 로직 (likeItem과 비슷한 로직)
    public void unlikeItem(String userEmail, String itemId) {
        // Email을 기반으로 Member 엔터티를 조회
        Member member = memberService.findMemberByEmail(userEmail);

        // itemId를 기반으로 Item 엔터티를 조회
        Item item = searchService.getItemWithImage(itemId);

        if (member != null && item != null) {
            Optional<Like> like = likeRepository.findByMemberidAndItem(item, member);
            if (like.isPresent()) {
                likeRepository.delete(like.get());
            }
        } else {
            throw new IllegalArgumentException("Invalid user or item.");
        }
    }
}