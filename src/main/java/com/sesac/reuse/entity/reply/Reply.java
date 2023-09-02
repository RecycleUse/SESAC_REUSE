package com.sesac.reuse.entity.reply;

import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.member.Member;

import javax.persistence.*;

@Entity
public class Reply extends BaseEntity {
    @Column(name="reply_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") // FK관리
    private Member writert;

    @JoinColumn(name="board_id")
    @ManyToOne
    private Board board;

    private String content;
}