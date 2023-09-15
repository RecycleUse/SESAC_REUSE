package com.sesac.reuse.entity.itemSearch;

import lombok.*;
import com.sesac.reuse.entity.member.Member;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // JPA 즉시 로딩
    @JoinColumn(name = "email")
    private Member memberid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Item item;

    public Like() {

    }

    public Like(Member memberid, Item item) {
        this.memberid = memberid;
        this.item = item;
    }

}
