package com.sesac.reuse.dto;

import com.sesac.reuse.model.entity.Image;
import com.sesac.reuse.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemWithImagesDTO {
    private Item item;
    private List<Image> images;

    public String getItemName(){
        return item.getItemName();
    }
}
