package com.sesac.reuse.entity.member;

import javax.persistence.*;

//회원탈퇴시, Member테이블에 memberId(PK), email, del = true(탈퇴여부) 필드값만 유지시키고
//나머지는 null처리? (DB)

@Entity
public class InactiveMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    private String email;
}
