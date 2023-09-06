package com.sesac.reuse.security.dto;


import com.sesac.reuse.entity.member.MEMBER_STATUS;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.entity.member.SocialSignUpInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;



public class MemberSecurityDTO extends User {

    private Long memberId; //DB에서 넘어오는 PK값
    private String email; // Authentication의 username으로 사용될 필드
    private String pw;
    private String nickname;
    private MEMBER_STATUS isActive; //회원 탈퇴 여부
    private SocialSignUpInfo social;


    public MemberSecurityDTO(Member member,
                             Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPw(), authorities);
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.isActive = member.getIsActive();
        this.social = member.getSocial();

    }
}
