package com.sesac.reuse.service.board;

import com.sesac.reuse.dto.board.BoardDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO) throws Exception;

    BoardDTO readOne(Long boardId);

    void modify(BoardDTO boardDTO);

    void remove(Long boardId);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
