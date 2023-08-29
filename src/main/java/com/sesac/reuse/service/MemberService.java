package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;


public interface MemberService {

    static class EmailExistException extends RuntimeException{ } //패키지 빼고싶은데

    void join(MemberDTO memberDTO) throws EmailExistException;
}
