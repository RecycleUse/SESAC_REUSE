package com.sesac.reuse.security.service;

import com.sesac.reuse.config.entity.User;
import com.sesac.reuse.config.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("executing loadUserByEmail....");

        Optional<User> userOptional = userRepository.findByEmail(email);

        //get()으로 꺼내버리면 NPE터짐,
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        User user = userOptional.get();



    }
}
