package com.sesac.reuse.repository.board;

import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {

    @Query(value="select now()", nativeQuery = true) //@Query 이용해서 JPQL적용
    String getTime();


}
