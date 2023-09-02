package com.sesac.reuse.controller;

import com.sesac.reuse.repository.member.MemberRepository;
import com.sesac.reuse.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MemberController.class)
@Log4j2
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    PasswordEncoder passwordEncoder;


    @WithMockUser(username = "testUser", password = "1234")
    @Test
    void signupUsingMock() {
        //given
//        memberController.login();

        //then
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("user={}", authentication.getPrincipal().toString());
    }

//    @Test  <-- old버전 테코로 변경되야함
//    void signUpTest() {
//        //given
//        MemberDTO signupMemberDTO = MemberDTO.builder()
//                .email("test5@naver.com")
//                .pw("password")
//                .confirmPw("password")
//                .nickname("테스트유저")
//                .build();
//
//        //when
//        memberController.signUp(signupMemberDTO);
//
//        //then
//    }

    @Test
    @WithMockUser(username = "test@naver.com",password = "1234", roles = "MEMBER")
    void modifyProfileTest() throws Exception {

        mockMvc.perform(post("/member/modify-profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("pw", "changePw")
                        .param("confirmPw", "changePw")
                        .param("nickname", "changeNickname")
                        .param("email", "test@naver.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/member/profile"));


    }

}