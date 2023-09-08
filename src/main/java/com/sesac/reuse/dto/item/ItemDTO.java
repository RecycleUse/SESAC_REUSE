package com.sesac.reuse.dto.item;

import com.sesac.reuse.entity.item.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private String id;

    private String name;

    private String recycleInfo;

    private Boolean recyclable;

    private LocalDateTime createdAt;

    private Category category;

    private MultipartFile image;

}
