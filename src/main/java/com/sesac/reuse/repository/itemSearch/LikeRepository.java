package com.sesac.reuse.repository.itemSearch;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.entity.itemSearch.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Transactional
    void deleteByMemberAndItem(Long member, Item item);

}