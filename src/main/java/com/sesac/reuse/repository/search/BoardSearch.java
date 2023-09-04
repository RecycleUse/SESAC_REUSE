package com.sesac.reuse.repository.search;

import com.sesac.reuse.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable); //테스트용

    Page<Board> searchAll(SearchOption searchOption, String keyword, Pageable pageable);
}
