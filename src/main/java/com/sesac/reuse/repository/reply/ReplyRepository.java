package com.sesac.reuse.repository.reply;

import com.sesac.reuse.entity.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Query("select r from Reply r where r.board.boardId = :boardId")
    Page<Reply> listOfBoard(@Param("boardId") Long boardId, Pageable pageable); //굳이 댓글 페이징 처리해야할까?

}
