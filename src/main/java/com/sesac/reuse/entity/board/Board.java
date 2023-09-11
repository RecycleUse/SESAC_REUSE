package com.sesac.reuse.entity.board;


import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.member.Member;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    private Type type; //글 종류 분류
    private String title;
    private String content;
    private String nickname; //닉네임 픽스시키려고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") //
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    @PreAuthorize("isAuthenticated()")
    public void change(Type type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;

    }

    @PreAuthorize("hasRole('ADMIN')")
    public void changeForAdmin(Type type, String title, String content, Status status) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
    }

}
