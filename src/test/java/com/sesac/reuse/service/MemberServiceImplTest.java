package com.sesac.reuse.service;

import com.sesac.reuse.dto.member.MemberDTO;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.member.MemberRepository;
import com.sesac.reuse.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Test
    void modifyProfileTest() {
        //given
        MemberDTO modifyMemberDTO = MemberDTO.builder()
                .email("test@naver.com")
                .pw("password1")
                .confirmPw("password1")
                .nickname("닉네임변경테스트")
                .build();

        //when
        memberService.modifyProfile(modifyMemberDTO);

        //then

        Member findMember = memberRepository.findByEmail(modifyMemberDTO.getEmail()).orElseThrow();

        Assertions.assertThat(findMember.getNickname()).isEqualTo(modifyMemberDTO.getNickname());


    }




}