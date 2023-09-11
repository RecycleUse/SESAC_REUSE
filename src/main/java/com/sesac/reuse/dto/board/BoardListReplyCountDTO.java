package com.sesac.reuse.dto.board;

import com.sesac.reuse.entity.board.Status;
import com.sesac.reuse.entity.board.Type;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardListReplyCountDTO {

    private Long boardId;
    private String title;
    private String nickname;
    private LocalDateTime regDate;
    private Status status;
    private Type type;

    private Long replyCount;
}
