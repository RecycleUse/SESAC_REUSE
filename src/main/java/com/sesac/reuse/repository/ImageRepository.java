package com.sesac.reuse.repository;

import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByItem(Item item);
}