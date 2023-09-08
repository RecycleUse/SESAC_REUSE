package com.sesac.reuse.repository.item;

import com.sesac.reuse.entity.item.Image;
import com.sesac.reuse.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByItem(Item item);
}