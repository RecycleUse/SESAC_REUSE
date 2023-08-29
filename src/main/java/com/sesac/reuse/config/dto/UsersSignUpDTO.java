package com.sesac.reuse.config.dto;

import com.sesac.reuse.config.entity.SocialSignUpInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

//    로그인시에는 Authentication 타입으로 담아줘야하기 때문에 회원가입용 DTO 와 분리!
public class UsersSignUpDTO {

    @NotEmpty
    private String email;

    @NotEmpty
    private String pw;
    @NotEmpty
    private String confirmPw;

    @NotEmpty
    private String nickname;
//    private boolean del; //회원 탈퇴 여부 <-- form에서 넘어올 값이 아니니까 저장할 때 하는거같음
//    private boolean social;


}
