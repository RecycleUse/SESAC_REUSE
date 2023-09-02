package com.sesac.reuse.entity.board;

import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.entity.reply.Reply;

import javax.persistence.*;
import java.util.List;


@Entity
public class Board extends BaseEntity {

    @Column(name="board_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    private Type type; //글 종류 분류
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") // FK관리
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Reply> replies;

    @Enumerated(EnumType.STRING)
    private Status status;

}
