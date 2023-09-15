package com.sesac.reuse.entity.itemSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sesac.reuse.entity.member.Member;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  // JPA를 사용하여 테이블과 매핑할 클래스임을 나타냅니다.
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "item")
    @JsonIgnore  // 순환 참조 방지
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member member;

    @OneToMany(mappedBy = "item")
    private Set<Like> likes = new HashSet<>();

}
