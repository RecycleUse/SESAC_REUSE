package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.dto.MemberProfileDTO;
import com.sesac.reuse.exception.EmailExistException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface MemberService {


    public void join(MemberDTO memberDTO) throws EmailExistException;
    public MemberDTO findProfileByEmail(String email) throws UsernameNotFoundException;

    public void modifyProfile(MemberDTO memberDTO);

    public boolean isExistAccount(String email);
}
