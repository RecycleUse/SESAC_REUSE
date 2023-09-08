package com.sesac.reuse.service.itemAdmin;

import com.sesac.reuse.dto.item.ItemDTO;
import com.sesac.reuse.entity.item.Image;
import com.sesac.reuse.entity.item.Item;
import com.sesac.reuse.repository.item.CategoryRepository;
import com.sesac.reuse.repository.item.ImageRepository;
import com.sesac.reuse.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ItemService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    private final String UPLOAD_DIR = "C:\\upload\\";

    @Autowired
    public ItemService(CategoryRepository categoryRepository, ItemRepository itemRepository, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
    }

    // 카테고리별 아이템 조회
//    public List<Item> getItemsByCategoryId(String category_id) {
//        return itemRepository.getItemsByCategoryId(category_id);
//    }

    // 아이템 조회
    public Item getItem(String itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        Optional<Image> itemImage = imageRepository.findByItemId(itemId);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new RuntimeException();
        }
    }


    // 아이템 생성
    public Item createItem(Item item) {

        Item newItem = Item.builder()
                .id(item.getId())
                .name(item.getName())
                .recyclable(item.getRecyclable())
                .recycleInfo(item.getRecycleInfo())
                .category(item.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        return itemRepository.save(newItem);
    }


    // 아이템 수정
    public Item updateItem(Item item) {
        Optional<Item> optional = itemRepository.findById(item.getId());

        return optional.map(entityItem -> {
                    // data -> update
                    entityItem.setId(item.getId())
                            .setName(item.getName())
                            .setRecycleInfo(item.getRecycleInfo())
                            .setRecyclable(item.getRecyclable())
                            .setCategory(item.getCategory());
                    return itemRepository.save(entityItem);
                })
                .orElseThrow(() -> new RuntimeException("Update error"));
    }


    // 아이템 삭제
    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }

    // 아이템 목록 조회
    public Page<Item> getList(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, 10, sort);
        return itemRepository.findAll(pageable);
    }
    
    // 이미지 파일 조회
//    public ItemImage getItemImage(String itemId) {
//        Optional<ItemImage> itemImage = imageRepository.findByItemId(itemId);
//        if (itemImage.isPresent()) {
//            return itemImage.get();
//        } else {
//            throw new RuntimeException();
//        }
//    }

    public void saveItem(ItemDTO itemDto) throws IOException {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setCategory(itemDto.getCategory());
        item.setCreatedAt(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);

        MultipartFile file = itemDto.getImage();
        String imagePath = storeFile(itemDto.getImage());
        Image itemImage = new Image();
        itemImage.setItem(savedItem);
        itemImage.setName(file.getOriginalFilename());
        itemImage.setPath(imagePath + file.getOriginalFilename());
        imageRepository.save(itemImage);

        // Save the file to the filesystem
        File fileToSave = new File(UPLOAD_DIR + file.getOriginalFilename());
        file.transferTo(fileToSave);
    }

    private String storeFile(MultipartFile file) throws IOException {
        // Logic to save the file to a server directory and return the path
        return "/static2/images/item_images/"; // This is just a placeholder
    }

}
