package com.sesac.reuse.repository.reply;

import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.reply.Reply;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@SpringBootTest
@Log4j2
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void insert() {

        Long boardId = 30L;
        Board board = Board.builder().boardId(boardId).build();

        Reply reply = Reply.builder().board(board).writer("yejins").content("댓글 작성 테스트").build();

        Reply save = replyRepository.save(reply);

        Assertions.assertThat(save.getContent()).isEqualTo(reply.getContent());


    }

    @Test
    void listOfBoard() {

        Long boardId = 30L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("replyId").descending());

        Page<Reply> result = replyRepository.listOfBoard(boardId, pageable);

        result.getContent().forEach(reply -> {
            log.info("reply={}",reply);
        });
    }
}