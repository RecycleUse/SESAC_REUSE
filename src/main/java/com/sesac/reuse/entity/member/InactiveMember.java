package com.sesac.reuse.entity.member;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

//회원탈퇴시, Member테이블에 memberId(PK), email, del = true(탈퇴여부) 필드값만 유지시키고
//나머지는 null처리? (DB)
//Member 테이블이랑 InactiveMember 테이블을 엮어놓으면 문제 생기니까 별도로 관리 (연관관계x)
//id, email값은 복사해서 값을 넣는 방식으로

@Entity
public class InactiveMember  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Long memberId;


    @CreatedDate
    @Column(name="inactive_data", updatable = false)
    private LocalDateTime inactiveDate;
}
