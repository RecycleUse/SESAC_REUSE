package com.sesac.reuse.repository.itemSearch;

import com.sesac.reuse.entity.itemSearch.Image;
import com.sesac.reuse.entity.itemSearch.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByItem(Item item);
}