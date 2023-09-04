package com.sesac.reuse.entity.reply;

import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.member.MEMBER_STATUS;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@ToString(exclude = "board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Entity
//@Table(name="reply", indexes = { //게시물당 댓글 수, 댓글 목록을 조회하다보니, 댓글 인덱스로 board_id를 사용
//        @Index(name="idx_reply_board_boardId",columnList = "board_board_id")
//})
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; //Reply가 연관관계 주인 , FK 관리

    private String writer;

    private String content;

    private MEMBER_STATUS isActive;
}
