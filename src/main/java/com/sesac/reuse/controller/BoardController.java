package com.sesac.reuse.controller;

import com.sesac.reuse.dto.board.BoardListReplyCountDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.BoardDTO;
import com.sesac.reuse.service.Board.BoardService;
import com.sesac.reuse.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping()
    public String board() {
        return "board";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO={}",pageRequestDTO);

        PageResponseDTO<BoardListReplyCountDTO> responseDTO = boardService.listWithReplyCount(pageRequestDTO);
        log.info("responseDTO={}",responseDTO);
        model.addAttribute("responseDTO",responseDTO);


        return "board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("board", new Board());
        return "board/register";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(@Valid BoardDTO boardDTO,
                           BindingResult bindingResult,
                           Principal principal) throws Exception {

        log.info("boardDTO={}",boardDTO);

        if(bindingResult.hasErrors()) {
            log.error("BoardDTO Validation error exist");

            bindingResult.getFieldErrors().forEach(fieldError -> {
                log.info("fieldError={}",fieldError.getField().toString());
            });

            return "redirect:/board/register";
        }


        try {

            if(principal.getName() == null) {
                return "redirect:/auth2/login";
            }


            Long savedBoardId = boardService.register(boardDTO, principal.getName());

            return "redirect:/board/list";

        } catch (Exception e) { //에러 좀 더 세분화 하기(추후)
            log.error(e);
            throw new Exception("게시글 저장 중 에러 발생");
        }

    }


    @GetMapping("/read") //쿼리파라미터
    public String read(Long boardId, Model model,Principal principal) {

        BoardDTO boardDTO = boardService.readOne(boardId);

        log.info("boardDTO.getMember={}",(boardDTO.getMember()));

        //비로그인 사람


        if (principal != null) {
            String username = principal.getName();

            if (!username.isEmpty() && username.equals(boardDTO.getMember().getEmail())) {
                model.addAttribute("writerReader", "true");
            }
        }


        log.info("boardDTO={}",boardDTO);

        model.addAttribute("dto",boardDTO);

        return "board/read";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String remove(@RequestParam("boardId") Long boardId, RedirectAttributes redirectAttributes) {


        log.info("boardId={}",boardId);
        boardService.remove(boardId);
        redirectAttributes.addFlashAttribute("result","removed");

        return "redirect:/board/list";
    }



}
