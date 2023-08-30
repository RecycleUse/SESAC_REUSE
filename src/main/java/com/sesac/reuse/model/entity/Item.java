package com.sesac.reuse.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  // JPA를 사용하여 테이블과 매핑할 클래스임을 나타냅니다.
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Item {

    @Id
    private String item_id;

    @Column(name = "item_name")  // 컬럼명을 지정해주어야 함
    private String itemName;

    private String recycle_info;

    private Boolean recyclable;

    @ManyToOne  // Item N : 1 Category
    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category;

}