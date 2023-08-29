package com.sesac.reuse.security.dto;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;
/*
    로그인시에는 Authentication 타입으로 담아줘야하기 때문에 회원가입용 DTO 와 분리!

    좀 더 세밀한 설정을 하려면 UserDetails 인터페이스를 구현
    좀 간단하게 할 때는 UserDetails인터페이스를 구현해놓은 User 클래스 이용
 */

public class UserSecurityDTO extends User {

    private Long userId; //DB에서 넘어오는 PK값
    private String email;
    private String pw;
    private String nickname;
    private boolean del; //회원 탈퇴 여부
    private boolean social; //우선 false로 설정하고 진행


    //db에서 조회한 값을 -> Authentication에 담아줄 때 사용할거니까
    public UserSecurityDTO(com.sesac.reuse.config.entity.User user,
                           Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPw(), authorities);
        this.userId = user.getUserId();
        this.email = user.getEmail(); //<-- 이게 필요한가?
        this.nickname = user.getNickname();
        this.del = user.isDel();
        this.social = user.ge





    }
}
