package com.sesac.reuse.entity.board;

import com.sesac.reuse.entity.member.Member;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private String nickname;

    private Member member;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;




}