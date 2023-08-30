package com.sesac.reuse.service;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.entity.Member;
import com.sesac.reuse.entity.MemberRole;
import com.sesac.reuse.entity.SocialSignUpInfo;
import com.sesac.reuse.exception.EmailExistException;
import com.sesac.reuse.exception.UserEmailNotFoundException;
import com.sesac.reuse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    }

    @Override
    public MemberDTO findProfileByEmail(String email) throws UserEmailNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if(memberOptional.isEmpty()) throw new UserEmailNotFoundException("해당 email을 가진 회원이 존재하지 않습니다.");

        return convertMemberDTO(memberOptional.get());

    }

    private MemberDTO convertMemberDTO(Member member) {

        return MemberDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
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