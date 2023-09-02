package com.sesac.reuse.repository.board;

import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.Status;
import com.sesac.reuse.entity.board.Type;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    //status, type 지정해주지 않으면 DB에 null값으로 들어감
    @Test
    void insetTest() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            Board board = Board.builder()
                    .title("테스트 타이틀" + i)
                    .content("테스트 컨텐츠" + i)
                    .build();

            Board savedBoard = boardRepository.save(board);
            log.info("board Id={}", savedBoard.getBoardId());
        });
    }

    @Test
    void selectTest() {
        //given
        Long boardId = 11L;

        //when
        Board board = boardRepository.findById(boardId).orElseThrow();

        //then
        Assertions.assertThat(board.getTitle()).isEqualTo("테스트 타이틀1");
        log.info("board={}",board);
    }

    @Test
    void updateTest() {
        //given , 11번 type, title 변경
        Long boardId = 11L;
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.change(Type.ADDITIONAL_REQUEST, "타이틀 변경", "컨텐츠 내용 변경");

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        Assertions.assertThat(savedBoard.getTitle()).isEqualTo(board.getTitle());
        log.info("savedBoard={}",savedBoard);
    }

    @Test
    void deleteTest() {
        //given
        Long id = 5L;

        //when
        boardRepository.deleteById(id); //<-- delete메서드 사용하면, JPA내부에서 select로 있는지부터 체크 후 delete 쿼리 나감(영속컨텍스트에 미리 불러온 다음 delete)

        //then
        Optional<Board> result = boardRepository.findById(id);
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    //Pageable 타입의 객체를 파라미터로 전달 => 페이징 처리
    //PageRequest.of(...) 를 통해 Pageable 타입의 구현체 생성
    //목록을 조회할 때, 조건을 줘서 페이징 처리하는거니까 findAll(Pageable 타입 객체) => Page<T> 리턴
    @Test



}