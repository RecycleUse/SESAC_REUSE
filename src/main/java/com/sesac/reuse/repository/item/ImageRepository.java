package com.sesac.reuse.repository.item;

import com.sesac.reuse.entity.item.Image;
import com.sesac.reuse.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByItem(Item item);

    // 아이템 이미지 조회
    @Query("SELECT i FROM Image i WHERE i.item.id = :item_id")
    Optional<Image> findByItemId(@Param("item_id") String item_id);

}