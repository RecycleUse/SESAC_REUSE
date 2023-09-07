package com.sesac.reuse.security.service;

import com.sesac.reuse.entity.member.MEMBER_STATUS;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.entity.member.MemberRole;
import com.sesac.reuse.entity.member.SocialSignUpInfo;
import com.sesac.reuse.repository.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

// 카카오 OAuth2 사용자 정보를 처리하기 위한 서비스 클래스로, 사용자 정보를 가져와서 회원 정보로 변환하고 권한을 부여
@Service  // 해당 클래스를 Spring의 Service로 등록합니다. 비즈니스 로직을 포함하는 클래스에 주로 사용됩니다.
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    @Autowired  // Spring의 DI를 사용하여 userRepository 인스턴스를 자동으로 주입받습니다.
    private MemberRepository memberRepository;

    private final HttpSession httpSession;

    @Override  // 부모 클래스의 메소드를 오버라이드 합니다.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);  // 부모 클래스의 loadUser 메소드를 호출하여 기본 OAuth2User를 가져옵니다.

        // 사용자의 OAuth2 인증 정보에서 가져온 속성들을 저장하는 Map 객체를 생성합니다.
        Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
        System.out.println(attributes);  // 가져온 속성들을 로그로 출력합니다.

        // 카카오 API로부터 반환된 "kakao_account" 속성을 맵으로 추출합니다.
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");  // "kakao_account"에서 "email" 속성을 가져옵니다.

        // "kakao_account"의 "profile" 속성을 맵으로 추출합니다.
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");  // "profile"에서 "nickname" 속성을 가져옵니다.

        // 데이터베이스에서 해당 이메일을 가진 사용자를 찾거나 생성
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = new Member(email, nickname);
                    newMember.setSocial(SocialSignUpInfo.KAKAO); // 소셜 정보 설정
                    newMember.addRole(MemberRole.MEMBER); // 기본 권한 추가
                    newMember.setIsActive(MEMBER_STATUS.ACTIVE); // 회원가입 시 활성화 상태로 설정
                    return memberRepository.save(newMember);
                });

        // 권한 정보 설정 (여기에서는 기본적으로 MEMBER 권한을 설정)
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_MEMBER"));

        // 사용자 정보를 포함한 OAuth2User 객체 생성하여 반환
        OAuth2User oauth2UserWithAuthorities = new DefaultOAuth2User(
                authorities, attributes, "email");

        return oauth2UserWithAuthorities; // OAuth2User 정보를 포함한 객체 반환
    }
}
