package com.sesac.reuse.service.board;

import com.sesac.reuse.dto.board.BoardDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.repository.board.BoardRepository;
import com.sesac.reuse.repository.search.SearchOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @Override
    public Long register(BoardDTO boardDTO) throws Exception {

        Board board = modelMapper.map(boardDTO, Board.class);

        try {
            return boardRepository.save(board).getBoardId();
        } catch (IllegalArgumentException e) { // <-- 무슨 에러발생하는지 찾아서 세밀하게 처리하기
            log.error("게시글 저장 중 오류 발생");
            throw new Exception("arguments error 발생", e);
        } catch (Exception e) {
            log.error(e);
            throw new Exception();
        }

    }

    @Override
    public BoardDTO readOne(Long boardId) {

        Optional<Board> result = boardRepository.findById(boardId);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        log.info("boardDTO={}", boardDTO);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBoardId());

        try {
            Board board = result.orElseThrow();
            board.change(boardDTO.getType(), boardDTO.getTitle(), boardDTO.getContent());

            boardRepository.save(board);
        } catch (NoSuchElementException e) {
            log.error(e);
        } catch (AccessDeniedException e) {
            log.error(e);
            throw new AccessDeniedException("권한이 없습니다.");
        }


    }

    @Override
    public void remove(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        SearchOption searchOption = pageRequestDTO.getSearchOption();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("boardId");
        log.info("searchOption={}",searchOption);
        log.info("keyword={}",keyword);
        log.info("pageable={}",pageable);

        Page<Board> result = boardRepository.searchAll(searchOption, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        dtoList.forEach(boardDTO -> {
            log.info("boardDTO={}", boardDTO);
        });

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }


}
