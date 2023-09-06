package com.sesac.reuse.controller;


import com.sesac.reuse.dto.member.MemberDTO;
import com.sesac.reuse.exception.EmailExistException;
import com.sesac.reuse.service.member.MemberService;
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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/auth2/login")
    public String loginPage() {
        return "member/login";
    }


    @GetMapping("/auth2/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }


    @GetMapping("/auth2/signup")
    public String signUpPage() {
        return "member/signup";
    }

    @PostMapping("/auth2/signup")
    public String signUp(@Valid MemberDTO memberDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        log.info("memberDTO={}", memberDTO);

        validatePwAndRedirect(memberDTO, bindingResult, "/auth2/signup");


        try {
            memberService.join(memberDTO);
        } catch (EmailExistException e) {
            log.error("이미 존재하는 회원입니다."); // 프론트단으로 에러보내주기
            redirectAttributes.addFlashAttribute("error", "email");

            return "redirect:/auth2/signup";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/auth2/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth2/profile")
    public String myProfile(Model model) {

        String principalEmail = getPrincipalEmail();

        MemberDTO profileDTO = memberService.findProfileByEmail(principalEmail);

        log.info("profileDTO={}", profileDTO);
        model.addAttribute("profileDTO", profileDTO);

        return "member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/auth2/modify-profile")
    public String modifyProfie(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {

        log.info("memberDTO={}", memberDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult.getAllErrors());
            return "member/profile";
        }

        validatePwAndRedirect(memberDTO, bindingResult, "/member/profile");

        memberService.modifyProfile(memberDTO);

        return "redirect:/auth2/profile";
    }


    private static String validatePwAndRedirect(MemberDTO memberDTO, BindingResult bindingResult, String redirectUrl) {
        if (!memberDTO.getPw().equals(memberDTO.getConfirmPw())) {
            bindingResult.rejectValue("pw", "passwordInCorrect", "비밀번호와 확인 비밀번호가 불일치합니다.");
            log.error("occur passwordInCorrect");

            return "redirect:" + redirectUrl;
        }
        return null;
    }

    private static String getPrincipalEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("email={}", email);

        return email;
    }

    @GetMapping("/auth2/reset-pwd")
    public String resetPwd() {
        log.info("호출됨");
        return "/auth2/reset-pwd";
    }

}
