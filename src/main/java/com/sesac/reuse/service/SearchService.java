package com.sesac.reuse.service;

import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.CategoryRepository;
import com.sesac.reuse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public SearchService(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    // 아이템 조회
    public Item getItem(String item_id) {
        Optional<Item> item = itemRepository.findById(item_id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new RuntimeException();
        }
    }

}
