package com.sesac.reuse.entity.itemSearch;

import lombok.*;
import com.sesac.reuse.entity.member.Member;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bmId")
    private Long bmId;

    @ManyToOne(fetch = FetchType.LAZY) // JPA 즉시 로딩
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Like(Member member, Item item) {
        this.member = member;
        this.item = item;

    }

}
