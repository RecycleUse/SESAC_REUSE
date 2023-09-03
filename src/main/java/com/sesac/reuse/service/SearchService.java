package com.sesac.reuse.service;

import com.sesac.reuse.dto.ItemWithImagesDTO;
import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import com.sesac.reuse.repository.CategoryRepository;
import com.sesac.reuse.repository.ImageRepository;
import com.sesac.reuse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    
    // 특정 아이템에 대한 이미지 정보를 가져오는 메서드
    public ItemWithImagesDTO getItem(String itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (!itemOptional.isPresent()) {
            throw new RuntimeException("Item not found");
        }

        Item item = itemOptional.get();
        List<Image> images = imageRepository.findByItem(item);

        return new ItemWithImagesDTO(item, images);  // 아이템과 이미지 정보를 함께 담아서 반환

        }


    // 특정 아이템에 대한 이미지를 저장하는 메서드
    public Image saveImageForItem(String itemId, MultipartFile file) throws IOException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (!itemOptional.isPresent()){
            throw new RuntimeException("아이템을 찾을 수 없습니다.");
        }

        Image img = new Image();
//        img.setData(file.getBytes());
        img.setImageName(file.getOriginalFilename());  // 파일의 전체 이름을 저장 (확장자 포함)
        img.setItem(itemOptional.get());

        return imageRepository.save(img);
    }


    // 특정 이미지의 데이터(이진 데이터 및 확장자)를 가져오는 메서드
    public Optional<Image> getImageData(String imageId){
        return imageRepository.findById(imageId);

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