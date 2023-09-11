package com.sesac.reuse.service.itemAdmin;

import com.sesac.reuse.dto.item.ItemDTO;
import com.sesac.reuse.entity.item.Image;
import com.sesac.reuse.entity.item.Item;
import com.sesac.reuse.repository.item.CategoryRepository;
import com.sesac.reuse.repository.item.ImageRepository;
import com.sesac.reuse.repository.item.ItemRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
        public void updateItem(@ModelAttribute ItemDTO itemDTO) throws IOException {
            Item item = itemRepository.findById(itemDTO.getId()).orElseThrow(() -> new RuntimeException("Item not found"));

            // update item
            item.setId(itemDTO.getId());
            item.setName(itemDTO.getName());
            item.setRecycleInfo(itemDTO.getRecycleInfo());
            item.setRecyclable(itemDTO.getRecyclable());
            item.setCategory(itemDTO.getCategory());

            Item saveItem = itemRepository.save(item);

            // save file
            String imagePath = storeFile(itemDTO.getImage());

            // update image
            MultipartFile file = itemDTO.getImage();
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()); // Apache Commons IO를 사용한 확장자 추출
            String newFilename = itemDTO.getId() + "_" + UUID.randomUUID().toString() + "." + fileExtension; // UUID로 새로운 파일명 생성
            Image image = saveItem.getImage();
            image.setItem(saveItem);
            image.setName(newFilename);
            image.setPath(imagePath + newFilename);
            imageRepository.save(image);

            // Save the file to the filesystem
            File fileToSave = new File(UPLOAD_DIR + newFilename);
            file.transferTo(fileToSave);

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

    // 아이템 생성
    public void saveItem(ItemDTO itemDto) throws IOException {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setRecyclable(itemDto.getRecyclable());
        item.setRecycleInfo(itemDto.getRecycleInfo());
        item.setCategory(itemDto.getCategory());
        item.setCreatedAt(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);

        MultipartFile file = itemDto.getImage();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()); // Apache Commons IO를 사용한 확장자 추출
        String newFilename = itemDto.getId() + "_" + UUID.randomUUID().toString() + "." + fileExtension; // UUID로 새로운 파일명 생성

        String imagePath = storeFile(itemDto.getImage());
        Image itemImage = new Image();
        itemImage.setItem(savedItem);
        itemImage.setName(newFilename);
        itemImage.setPath(imagePath + newFilename);
        imageRepository.save(itemImage);

        // 파일 저장
        File fileToSave = new File(UPLOAD_DIR + newFilename);
        file.transferTo(fileToSave);
    }

    private String storeFile(MultipartFile file) throws IOException {
        // Logic to save the file to a server directory and return the path
        return "static2/images/item_images/"; // This is just a placeholder
    }



    // 이미지 조회
//    public List<Image> getImageList(String itemId) {
//        Optional<Image> byItem = imageRepository.findByItemId(itemId);
//        return byItem;
//    }

}
