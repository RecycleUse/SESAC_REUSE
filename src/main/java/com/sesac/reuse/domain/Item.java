package com.sesac.reuse.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity  // JPA를 사용하여 테이블과 매핑할 클래스임을 나타냅니다.
@Getter  // Lombok을 사용하여 getter 메소드를 자동으로 생성합니다.
@Setter  // Lombok을 사용하여 setter 메소드를 자동으로 생성합니다.
@RequiredArgsConstructor  // Lombok을 사용하여 final이나 @NonNull 필드만 매개변수로 갖는 생성자를 생성합니다.
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "recycle_info")
    private String recycleInfo;

    private boolean recyclable;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}