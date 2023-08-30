package com.sesac.reuse.controller;

import com.sesac.reuse.dto.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;
import com.sesac.reuse.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 시큐리티 default login페이지를 안쓰고 커스텀 쓰는경우에는 GET요청 Controller 생성해줘야함
    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    //시큐리티 기본 제공 user로 로그인 테스트하면 "/login?error로 리다이렉트되고 SecurityContext 에 저장안됐다고 나오는데
    //익명 사용자(시큐리티 제공 user)라 세션(SecurityContext)에 저장 안하는 최적화임.
    //저장되는거 확인하고싶다면, 메모리 유저 or 테스트코드로


    @GetMapping("/signup")
    public String signUpPage() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid MemberDTO memberDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        log.info("memberDTO={}",memberDTO);

        //validation 부분이 안먹혀서 추가 검증 로직 필요
//        if(bindingResult.hasErrors()) {
//            log.error(bindingResult.getFieldError("email").toString());
//            model.addAttribute("memberDTO",memberDTO);
//            return "member/signup"; //타임리프는 앞에 /안붙이는게 적합
////            return "redirect:/member/signup";
//        }

        //비밀번호 검증
        if(!memberDTO.getPw().equals(memberDTO.getConfirmPw())) {
            bindingResult.rejectValue("pw","passwordInCorrect","비밀번호와 확인 비밀번호가 불일치합니다.");
            log.error("occur passwordInCorrect");
            return "redirect:/member/signup";
        }


        try {
            memberService.join(memberDTO);
        }catch (EmailExistException e) {
            log.error("이미 존재하는 회원입니다."); // 프론트단으로 에러보내주기
            redirectAttributes.addFlashAttribute("error","email"); //리다이렉트 컨트롤러에 세션(임시)로 담아 넘김 -> @ModelAttribute로 접근, 프론트단은 Model객체로 접근
            //여기선 어차피 GET으로가니까 컨트롤러에서는 접근할 필요없고, 프론트단에서만 접근하겠지
            return "redirect:/member/signup";
        }

        redirectAttributes.addFlashAttribute("result","success");
        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String myPage(Model model) {

        String principalEmail = getPrincipalEmail();

        MemberDTO profileDTO = memberService.findProfileByEmail(principalEmail);

        log.info("profileDTO={}",profileDTO);
        model.addAttribute("profileDTO",profileDTO);

        return "member/profile";
    }

    private static String getPrincipalEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("email={}",email);

        return email;
    }
}
