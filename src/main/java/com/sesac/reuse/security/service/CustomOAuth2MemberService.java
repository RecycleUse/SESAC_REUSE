package com.sesac.reuse.security.service;

import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.member.MemberRepository;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service  // 해당 클래스를 Spring의 Service로 등록합니다. 비즈니스 로직을 포함하는 클래스에 주로 사용됩니다.
@RequiredArgsConstructor
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
        Map<String, Object> profile1 = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile1.get("nickname");  // "profile"에서 "nickname" 속성을 가져옵니다.

//        // "kakao_account"의 "profile" 속성을 맵으로 추출합니다.
//        Map<String, Object> profile2 = (Map<String, Object>) kakaoAccount.get("profile");
//        String pw = (String) profile2.get("pw");  // "profile2"에서 "pw" 속성을 가져옵니다.

        // attributes 맵에 email과 nickname 속성을 추가합니다.
        attributes.put("email", email);
        attributes.put("nickname", nickname);
//        attributes.put("pw", pw);

        // 데이터베이스에서 해당 이메일을 가진 사용자를 찾습니다.
        // 없을 경우 새로운 User 객체를 생성하고 저장한 뒤 반환합니다.
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = new Member(email, nickname);
                    return memberRepository.save(newMember);
                });

        // OAuth2 인증에 사용될 사용자 정보를 담고 있는 DefaultOAuth2User 객체를 생성하여 반환합니다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),  // 기본 권한을 설정합니다.
                attributes,  // OAuth2 인증 정보의 속성들
                "email");  // 이름 필드의 키를 "email"로 설정합니다.
    }


}
