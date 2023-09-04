package com.sesac.reuse.service.board;

import com.sesac.reuse.dto.board.BoardDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.Type;
import com.sesac.reuse.repository.board.BoardRepository;
import com.sesac.reuse.repository.search.SearchOption;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Log4j2
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;


    @WithMockUser(authorities = "USER")
    @Test
    void register() throws Exception {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("서비스 계층 게시판 저장 테스트 타이틀")
                .content("서비스 계층 게시판 저장 테스트 내용")
                .type(Type.INQUIRY_ETC)
                .writer("예진쓰")
                .build();

        Long registedId = boardService.register(boardDTO);

       //(Service 조회 메서드 완성시키면 repository-> service로 수정해서 테스트)
//        Board findBoard = boardRepository.findById(registedId).orElseThrow();
        BoardDTO readBoardDTO = boardService.readOne(registedId);
        Assertions.assertThat(readBoardDTO.getTitle()).isEqualTo(boardDTO.getTitle());
    }

    @WithMockUser(authorities = "USER")
    @Test
    void registerFailCase() throws Exception {

        BoardDTO boardDTO = BoardDTO.builder()
                .content("title누락 케이스")
                .type(Type.INQUIRY_ETC)
                .writer("예진쓰")
                .build();

        boardService.register(boardDTO);

        Assertions.assertThatIllegalArgumentException();
    }



    @Test
    void readOne() {

        Long boardId = 33L;

        BoardDTO boardDTO = boardService.readOne(boardId);

        Assertions.assertThat(boardDTO.getBoardId()).isEqualTo(boardId);

    }
    @WithMockUser(authorities = "USER")
    @Test
    void modify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(30L)
                .content("수정 테스트 컨텐츠")
                .title("수정 테스트 타이틀")
                .build();

        boardService.modify(boardDTO);

        BoardDTO findBoard = boardService.readOne(boardDTO.getBoardId());

        Assertions.assertThat(findBoard.getTitle()).isEqualTo(boardDTO.getTitle());
    }

    @Test
    void list() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .keyword("1")
                .searchOption(SearchOption.CONTENT)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info("responseDTO={}",responseDTO);
    }
}