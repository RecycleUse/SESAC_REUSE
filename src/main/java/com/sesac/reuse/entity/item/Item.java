package com.sesac.reuse.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  // JPA를 사용하여 테이블과 매핑할 클래스임을 나타냅니다.
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Item {

    @Id
    @Column(name= "item_id")
    private String id;

    @Column(name = "item_name")
    private String name;  // 변수 이름 수정

    @Column(name = "recycle_info")
    private String recycleInfo;

    private Boolean recyclable;

    private LocalDateTime createdAt;

    @ManyToOne // Item N : 1 Category
    @JoinColumn(name = "category_id")
    @Enumerated(EnumType.STRING)
    private Category category;


    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore  // 순환 참조 방지
    private Image image;
}