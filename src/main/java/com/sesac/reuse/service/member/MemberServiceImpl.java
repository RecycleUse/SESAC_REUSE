package com.sesac.reuse.service.member;

import com.sesac.reuse.dto.member.MemberDTO;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.entity.member.MemberRole;
import com.sesac.reuse.entity.member.SocialSignUpInfo;
import com.sesac.reuse.exception.EmailExistException;
import com.sesac.reuse.exception.UserEmailNotFoundException;
import com.sesac.reuse.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        try {

            if (!isExistAccount(memberDTO.getEmail())) {
                Member member = convertMember(memberDTO);
                memberRepository.save(member);
            }
        } catch (EmailExistException e) {
            log.error("e");
            throw new EmailExistException();
        }


    }

    @Override
    public MemberDTO findProfileByEmail(String email) throws UserEmailNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isEmpty()) throw new UserEmailNotFoundException("해당 email을 가진 회원이 존재하지 않습니다.");

        return convertMemberDTO(memberOptional.get());

    }

    @Override
    @Transactional
    public void modifyProfile(MemberDTO memberDTO) {
        //nickname, pw 변경 가능
        Member member = memberRepository.findByEmail(memberDTO.getEmail()).orElseThrow();

        member.changeNickname(memberDTO.getNickname());
        member.changePw(passwordEncoder.encode(memberDTO.getPw()));

        memberRepository.save(member);
        log.info("service modifyProfile call");

    }

    private MemberDTO convertMemberDTO(Member member) {

        return MemberDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
    

    @Override
    public boolean isExistAccount(String email) {

        try {
            return memberRepository.existsByEmail(email);
        } catch (EmailExistException e) {
            log.error("e");
            throw new EmailExistException();
        }

    }

    @Override
    @Transactional
    public void resetPwd(String email, String tempPw) {

        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        findMember.changePw(passwordEncoder.encode(tempPw));
        memberRepository.save(findMember);
    }

    @Override
    public void withdrawMember(String email) {

            Member member = memberRepository.findByEmail(email).orElseThrow();
            memberRepository.delete(member);

    }

    public Member findMemberByEmail(String email) {
       return memberRepository.findByEmail(email).orElseThrow();
    }

    private Member convertMember(MemberDTO memberDTO) {
        Member member = mapper.map(memberDTO, Member.class);
        log.info("member={}", member);

        MemberRole role = memberDTO.getRole();

        member.encrptyPassword(passwordEncoder.encode(memberDTO.getPw()));
        member.addRole(MemberRole.MEMBER);
        member.addRole(role); //관리자
        member.setSocial(SocialSignUpInfo.STANDARD);
        log.info("member={}", member);
        return member;
    }

}
