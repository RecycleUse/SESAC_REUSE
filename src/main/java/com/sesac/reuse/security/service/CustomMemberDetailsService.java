package com.sesac.reuse.security.service;

import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.member.MemberRepository;
import com.sesac.reuse.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("executing loadUserByEmail....");

        Optional<Member> userOptional = memberRepository.findByEmail(email);


        if(userOptional.isEmpty()) {
            log.error("존재하지 않는 회원");
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        Member member = userOptional.get();
        log.info("member={}",member);
        List<SimpleGrantedAuthority> collect = member.getRoleSet().
                stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                .collect(Collectors.toList());

        log.info("collect={}",collect.stream().map(simpleGrantedAuthority -> {
            return simpleGrantedAuthority.getAuthority().toString();
        }));


        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member, member.getRoleSet().
                stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                .collect(Collectors.toList())
        );

        log.info("memberSecurityDTO={}", memberSecurityDTO);

        return memberSecurityDTO;

    }
}
