package com.sesac.reuse.dto.board;

import com.sesac.reuse.entity.board.Status;
import com.sesac.reuse.entity.board.Type;
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
public class BoardDTO {

    private Long id;

    private Type type;

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    private String nickname;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Enumerated(EnumType.STRING)
    private Status status;




}
