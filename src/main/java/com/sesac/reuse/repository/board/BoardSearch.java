package com.sesac.reuse.repository.board;

import com.sesac.reuse.dto.board.BoardListReplyCountDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.BoardSearchOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> searchAll(BoardSearchOption boardSearchOption, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(BoardSearchOption boardSearchOption, String keyword, Pageable pageable);

}
