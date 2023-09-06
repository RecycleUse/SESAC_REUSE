package com.sesac.reuse.dto.member;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String pw;
    @NotEmpty
    private String confirmPw;

    @NotEmpty
    @Length(min=3, max=20)
    private String nickname;

//    private boolean del; //회원 탈퇴 여부 <-- form에서 넘어올 값이 아니니까 저장할 때 하는거같음
//    private boolean social;


}
