package com.sesac.reuse.entity.itemSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "item_name")
    private String itemName;  // 변수 이름 수정

    private String recycle_info;

    private Boolean recyclable;

//    private String created_at;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "item")
    @JsonIgnore  // 순환 참조 방지
    private Image image;
}