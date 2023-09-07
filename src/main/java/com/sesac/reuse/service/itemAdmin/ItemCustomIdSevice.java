package com.sesac.reuse.service.itemAdmin;

import com.sesac.reuse.entity.item.Category;
import com.sesac.reuse.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemCustomIdSevice {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    // 카테고리별 마지막 세자리 수 + 1 값
    public String generateId(String categoryId) {
        int lastId = Integer.parseInt(itemRepository.findLastItemIdByCategoryId(categoryId));
        return categoryId + String.format("%03d", lastId + 1);
    }

    // 카테고리별 마지막 세자리 수 + 1 값 : map
    public Map<String, String> getItemCustomIdList() {
        Map<String, String> resultMap = new HashMap<>();
        List<Category> categories = categoryService.getCategoryList();

        for (int i = 0; i < categories.size(); i++) {
            String key = String.valueOf(categories.get(i).getCategoryId());
            String value = this.generateId(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }


}
