package com.sesac.reuse.repository.itemSearch;

import com.sesac.reuse.entity.itemSearch.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
//    Category getOne(CategoryId category_id);
}