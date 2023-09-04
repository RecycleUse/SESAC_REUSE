package com.sesac.reuse.service.itemSearch;

import com.sesac.reuse.entity.itemSearch.Image;
import com.sesac.reuse.entity.itemSearch.Item;
import com.sesac.reuse.repository.itemSearch.CategoryRepository;
import com.sesac.reuse.repository.itemSearch.ItemRepository;
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

    // 아이템 조회 및 이미지 정보 함께 가져오기
    public Item getItemWithImage(String item_id) {
        Optional<Item> itemOptional = itemRepository.findById(item_id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            // 아이템의 item_id를 사용하여 이미지 정보 가져오기
            Image image = item.getImage();
            if (image != null) {
                // 이미지 정보가 있을 경우 아이템에 설정
                item.setImage(image);
            }
            return item;
        } else {
            throw new RuntimeException("Item not found");
        }
    }
}

//    // 아이템 조회
//    public Item getItemWithImage(String item_id) {
//        Optional<Item> itemOptional = itemRepository.findById(item_id);
//        if (itemOptional.isPresent()) {
//            Item item = itemOptional.get();
//            // 이미지 정보를 함께 가져오기 위해 이미지 엔티티를 로딩
//            item.getImage();  // 이미지 정보를 가져오는 게터 메서드 호출
//            return item;
//        } else {
//            throw new RuntimeException("Item not found");
//        }
//    }
//}


//    public class ItemNotFoundException extends RuntimeException {
//
//        public ItemNotFoundException(String message) {
//            super(message);
//        }
//    }
//}
