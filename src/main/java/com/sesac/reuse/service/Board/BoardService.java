package com.sesac.reuse.service.Board;

import com.sesac.reuse.dto.board.BoardListReplyCountDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.entity.board.BoardDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO, String email) throws Exception; //email은 fk위해

    BoardDTO readOne(Long boardId);

    void modify(BoardDTO boardDTO);

    void remove(Long boardId);

    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

}
