package com.sesac.reuse.service.itemAdmin;

import com.sesac.reuse.entity.item.Category;
import com.sesac.reuse.repository.item.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }



}
