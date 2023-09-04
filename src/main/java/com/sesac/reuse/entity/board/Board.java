package com.sesac.reuse.entity.board;

import com.sesac.reuse.base.BaseEntity;

import com.sesac.reuse.entity.member.MEMBER_STATUS;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.persistence.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
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

    private String writer;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
//    private List<Reply> replies;

    @Enumerated(EnumType.STRING)
    private Status status; //처리 상태

    private MEMBER_STATUS isActive;

    @PreAuthorize("isAuthenticated()")
    public void change(Type type, String title, String content) {
        this.type = type; // <-- ANNOUNCEMENT 선택 방지는 Controller에서 처리하기
        this.title = title;
        this.content = content;

    }
    @PreAuthorize("hasRole('ADMIN')") //  @PreAuthorize 메서드 수준 제어 -> Config에 @EnableGlobalMethodSecurity(prePostEnabled = true) 설정필요
    public void changeForAdmin(Type type, String title, String content, Status status) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = status;
    }


}
