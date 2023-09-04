package com.sesac.reuse.dto.board;


import com.sesac.reuse.base.BaseEntity;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.Status;
import com.sesac.reuse.entity.board.Type;
import com.sesac.reuse.entity.member.Member;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO  {

    private Long boardId;

    private Type type;

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer; // DTO<-> Entity 변환시 setter로 직접 주입하는 주의 필요!
    @Nullable
    private Status status;

    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
