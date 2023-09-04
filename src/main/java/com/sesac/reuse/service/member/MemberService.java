package com.sesac.reuse.service.member;

import com.sesac.reuse.dto.member.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface MemberService {


    public void join(MemberDTO memberDTO) throws EmailExistException;
    public MemberDTO findProfileByEmail(String email) throws UsernameNotFoundException;

    public void modifyProfile(MemberDTO memberDTO);
    public boolean isExistAccount(String email);

    public void resetPwd(String email,String tempPw);
}
