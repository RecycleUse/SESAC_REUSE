package com.sesac.reuse.security.service;

import com.sesac.reuse.entity.member.MEMBER_STATUS;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.entity.member.MemberRole;
import com.sesac.reuse.entity.member.SocialSignUpInfo;
import com.sesac.reuse.repository.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// 카카오 OAuth2 사용자 정보를 처리하기 위한 서비스 클래스로, 사용자 정보를 가져와서 회원 정보로 변환하고 권한을 부여
@Service
@RequiredArgsConstructor
@Log4j2
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);  // 기본 OAuth2 사용자 정보를 가져옴

        // attributes라는 Map을 생성하고, 이것에 OAuth2 사용자의 속성을 복사함 (oauth2User.getAttributes()로 속성을 가져옴)
        Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
        log.info(attributes);  // 가져온 속성들을 로그로 출력


        // 카카오 API로부터 반환된 "kakao_account" 속성을 맵으로 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");  // "kakao_account"에서 "email" 속성을 가져옴


        // 이메일 정보가 없으면 로그인을 막음
        if (email == null || email.isEmpty()) {
            throw new UsernameNotFoundException("카카오 이메일 정보가 없습니다.");
        }


        // 카카오 API로부터 반환된 "profile" 속성을 맵으로 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");  // "profile"에서 "nickname" 속성을 가져옴

        // attributes Map에 email과 nickname 속성 추가
        attributes.put("email", email);
        attributes.put("nickname", nickname);


        // 데이터베이스에서 해당 이메일을 가진 사용자를 찾거나, 없다면 새로운 회원 생성
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = new Member(email, nickname);
                    newMember.setSocial(SocialSignUpInfo.KAKAO);  // 소셜 정보 설정
                    newMember.addRole(MemberRole.MEMBER);  // 기본 권한 추가
                    newMember.setIsActive(MEMBER_STATUS.ACTIVE);  // 회원가입 시 활성화 상태로 설정
                    return memberRepository.save(newMember);
                });

        // OAuth2 인증에 사용될 사용자 정보를 담고 있는 DefaultOAuth2User 객체를 생성하여 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")),  // 기본 권한 설정
                attributes,  // OAuth2 인증 정보의 속성들
                "email");  // 이름 필드의 키를 "email"로 설정
    }
}
