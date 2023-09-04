package com.sesac.reuse.entity.itemSearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "item_image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long image_id;

    @Column(name = "image_path")
    private String image_path;

    @Column(name = "image_name")
    private String image_name;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}