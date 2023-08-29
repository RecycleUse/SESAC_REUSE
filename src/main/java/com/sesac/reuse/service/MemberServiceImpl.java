package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.entity.Member;
import com.sesac.reuse.entity.MemberRole;
import com.sesac.reuse.entity.SocialSignUpInfo;
import com.sesac.reuse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;


    @Override
    public void join(MemberDTO memberDTO) throws EmailExistException {

        isExistAccount(memberDTO);
        Member member = convertMember(memberDTO);
        memberRepository.save(member);

        log.info("가입 완료");

    }

    private Member convertMember(MemberDTO memberDTO) {
        Member member = mapper.map(memberDTO, Member.class);
        log.info("member={}", member);
        member.encrptyPassword(passwordEncoder.encode(memberDTO.getPw()));
        member.addRole(MemberRole.MEMBER);
//        member.addRole(MemberRole.ADMIN); <--권한 2개 테스트, 관리자 가입 로직 별도로 생성하기
        member.setSocial(SocialSignUpInfo.STANDARD);
        return member;
    }

    private void isExistAccount(MemberDTO memberDTO) {
        boolean exist = memberRepository.existsByEmail(memberDTO.getEmail());

        if(exist) throw new EmailExistException();
    }
}
