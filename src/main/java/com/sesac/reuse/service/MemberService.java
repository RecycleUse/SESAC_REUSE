package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface MemberService {


    void join(MemberDTO memberDTO) throws EmailExistException;
    MemberDTO findProfileByEmail(String email) throws UsernameNotFoundException;
}
