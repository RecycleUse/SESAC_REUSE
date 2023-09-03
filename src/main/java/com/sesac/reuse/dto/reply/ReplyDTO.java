package com.sesac.reuse.dto.reply;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyDTO {

    private Long replyId;
    @NotNull
    private Long boardId;
    @NotEmpty
    private String writer;
    @NotEmpty
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
