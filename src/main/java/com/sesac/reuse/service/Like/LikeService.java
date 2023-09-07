package com.sesac.reuse.service.Like;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.entity.itemSearch.Like;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.itemSearch.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void likeItem(Long userId, Item item) {
        // 아이템을 좋아요 처리하는 로직을 구현
        // 좋아요 정보를 `likes` 테이블에 추가
        Like like = new Like();
        Member member = new Member();
        member.setId(userId);
        like.setMember(member);
        like.setItem(item);
        likeRepository.save(like);
    }

    public void unlikeItem(Long userId, Item item) {
        // 아이템의 좋아요를 취소하는 로직을 구현
        // `likes` 테이블에서 해당 정보를 제거
        likeRepository.deleteByMemberAndItem(userId, item);
    }
}