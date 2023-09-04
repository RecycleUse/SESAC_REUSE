package com.sesac.reuse.controller;

import com.sesac.reuse.dto.board.BoardDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.service.board.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public String board() {
        return "board";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO={}",pageRequestDTO);

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info("responseDTO={}",responseDTO);
        model.addAttribute("responseDTO",responseDTO);

        return "/board";
    }
}
