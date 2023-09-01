package com.sesac.reuse.repository;

import java.util.List;
import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findByItem(Item item);
}
