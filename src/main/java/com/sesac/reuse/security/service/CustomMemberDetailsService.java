package com.sesac.reuse.security.service;

import com.sesac.reuse.entity.Member;
import com.sesac.reuse.repository.MemberRepository;
import com.sesac.reuse.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/*
    시큐리티config의 loginForm()으로 로그인 요청이 들어오면 내부적으로 Controller수행이되고, UserDetailsService의 loadUserByUsername()
    메서드를 수행하게 되고, 여기서 username으로 사용자를 찾고, 사용자가 존재하면 UserDetails 객체를 반환함
    이 UserDetaisl안에는 User 정보가 담김

    loadUserByUsername()에서 UserDetaisl을 반환하면, 그 중간 부분에서 Authentication 객체에 담고 -> SecurityContextHolder(시큐리티 세션)에 담아서
    호출한 곳(필터단)으로 던져줌!

 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("executing loadUserByEmail....");

        Optional<Member> userOptional = memberRepository.findByEmail(email);

        //get()으로 꺼내버리면 NPE터짐,
        if(userOptional.isEmpty()) {
            log.error("존재하지 않는 회원");
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        Member member = userOptional.get();

        //member-> MemberSecurityDTO로 반환해줘야함
        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(member, member.getRoleSet().
                stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                .collect(Collectors.toList())
        );

        log.info("memberSecurityDTO={}", memberSecurityDTO);

        return memberSecurityDTO;

    }
}
