package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;


public interface MemberService {


    void join(MemberDTO memberDTO) throws EmailExistException;
}
