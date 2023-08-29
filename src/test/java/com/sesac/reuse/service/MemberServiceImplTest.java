package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.entity.Member;
import com.sesac.reuse.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  ModelMapper mapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void joinTest() {
        //given
        MemberDTO signupMemberDTO = MemberDTO.builder()
                .email("test@naver.com")
                .pw("password")
                .confirmPw("password")
                .nickname("테스트유저")
                .build();

        //when
        memberService.join(signupMemberDTO);

        //then
        Member findMember = memberRepository.findByEmail("test@naver.com").orElseThrow();
        Assertions.assertThat(findMember.getEmail()).isEqualTo(signupMemberDTO.getEmail());
    }



}