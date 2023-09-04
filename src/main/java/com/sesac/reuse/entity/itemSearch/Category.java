package com.sesac.reuse.entity.itemSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Category {

    @Id
    @Enumerated(EnumType.STRING)
    private CategoryId category_id;

    private String name;

    // Category 1 : N Item
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnore // 순환 참조 끊기 (검색창 드롭다운 기능에 사용)
    private List<Item> itemList;
}
