package com.sesac.reuse.entity.reply;

import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.board.Board;
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
@Table(name="reply", indexes = {
        @Index(name="idx_reply_board_id",columnList = "board_Id")
})
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @JoinColumn(name="board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String writer;

    private String content;

    public void changeContent(String content) {
        this.content = content;
    }

}
