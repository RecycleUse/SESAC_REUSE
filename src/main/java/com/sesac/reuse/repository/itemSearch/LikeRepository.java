package com.sesac.reuse.repository.itemSearch;

import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.entity.itemSearch.Like;
import com.sesac.reuse.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByMemberidAndItem(Member member, Item item);

    Optional<Like> findByMemberidAndItem(Item item, Member member);

}