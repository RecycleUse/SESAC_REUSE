package com.sesac.reuse.service;

import com.sesac.reuse.dto.ItemWithImagesDTO;
import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.CategoryRepository;
import com.sesac.reuse.repository.ImageRepository;
import com.sesac.reuse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public SearchService(CategoryRepository categoryRepository, ItemRepository itemRepository, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
    }

    public ItemWithImagesDTO getItem(String item_id) {
        Optional<Item> itemOptional = itemRepository.findById(item_id);

        if (!itemOptional.isPresent()) {
            throw new RuntimeException("Item not found");
        }

        Item item = itemOptional.get();
        List<Image> images = imageRepository.findByItem(item);

        return new ItemWithImagesDTO(item, images);  // 아이템과 이미지 정보를 함께 담아서 반환

        }
    }

        // 아이템 조회
        //    public Item getItem(String item_id) {
        //        Optional<Item> item = itemRepository.findById(item_id);
        //        if (item.isPresent()) {
        //            return item.get();
        //        } else {
        //            throw new RuntimeException();
        //        }
        //    }
