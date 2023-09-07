//package com.sesac.reuse.repository.board;
//
//import com.sesac.reuse.entity.board.Board;
//import com.sesac.reuse.entity.board.Type;
//import com.sesac.reuse.repository.search.SearchOption;
//import lombok.extern.log4j.Log4j2;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//@Log4j2
//class BoardRepositoryTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    //status, type 지정해주지 않으면 DB에 null값으로 들어감
//    @Test
//    void insetTest() {
//        IntStream.rangeClosed(1,10).forEach(i -> {
//            Board board = Board.builder()
//                    .title("테스트 타이틀" + i)
//                    .content("테스트 컨텐츠" + i)
//                    .build();
//
//            Board savedBoard = boardRepository.save(board);
//            log.info("board Id={}", savedBoard.getBoardId());
//        });
//    }
//
//    @Test
//    void selectTest() {
//        //given
//        Long boardId = 11L;
//
//        //when
//        Board board = boardRepository.findById(boardId).orElseThrow();
//
//        //then
//        Assertions.assertThat(board.getTitle()).isEqualTo("테스트 타이틀1");
//        log.info("board={}",board);
//    }
//
//    @Test
//    void updateTest() {
//        //given , 11번 type, title 변경
//        Long boardId = 11L;
//        Board board = boardRepository.findById(boardId).orElseThrow();
//        board.change(Type.ADDITIONAL_REQUEST, "타이틀 변경", "컨텐츠 내용 변경");
//
//        //when
//        Board savedBoard = boardRepository.save(board);
//
//        //then
//        Assertions.assertThat(savedBoard.getTitle()).isEqualTo(board.getTitle());
//        log.info("savedBoard={}",savedBoard);
//    }
//
//    @Test
//    void deleteTest() {
//        //given
//        Long id = 5L;
//
//        //when
//        boardRepository.deleteById(id); //<-- delete메서드 사용하면, JPA내부에서 select로 있는지부터 체크 후 delete 쿼리 나감(영속컨텍스트에 미리 불러온 다음 delete)
//
//        //then
//        Optional<Board> result = boardRepository.findById(id);
//        Assertions.assertThat(result.isPresent()).isFalse();
//    }
//
//    /*Pageable 타입의 객체를 파라미터로 전달 => 페이징 처리
//        PageRequest.of(...) 를 통해 Pageable 타입의 구현체 생성
//        PageRequest.of(페이지번호,사이즈,Sort) :가변인자
//    예) 목록을 조회할 때, 조건을 줘서 페이징 처리하는거니까 findAll(Pageable 타입 객체) => Page<T> 객체 리턴 => 출력데이터DTO로 변환 후 리턴
//    JpaRepository에서는 findAll() 을 통해 기본적인 페이징 처리 지원
//
//    반환되는 Page<T> 객체에는, 데이터 총 갯수 (getTotalElements)<--그래서 전체갯수 count쿼리도 별도로 발생, 해당 페이지 데이터(getContent) 등 다양한 기능 지원
//    */
//    @Test
//    void testPaging() {
//        //1pag, 10개의 데이터, 내림차순
//        //given
//        PageRequest pageable = PageRequest.of(0, 10, Sort.by("boardId").descending());
//
//        //when
//        Page<Board> result = boardRepository.findAll(pageable);
//
//        //부가
//        result.forEach(board -> {
//            log.info(board.getContent().toString());
//        });
//
//        log.info("page size = {}", result.getSize()); //페이지에 가져온 데이터 수
//        List<Board> boardList = result.getContent();
//        boardList.forEach(board -> {
//            log.info(board.toString());
//        });
//
//
//        //then
////        Assertions.assertThat(result.getTotalElements()).isEqualTo(10); , getTotalElements() : 데이터 총 갯수
//        Assertions.assertThat(result.getContent()).hasSize(10);
//    }
//
//
//    @Test
//    void testSearch1Test() {
//        //given, 2page 내림차순
//        Pageable pageable = PageRequest.of(2 - 1, 10, Sort.by("boardId").descending());
//
//
//        //when
//        Page<Board> boards = boardRepository.search1(pageable);
//
//
//        //then
//
//    }
//
//    @Test
//    void testSearchAllTest() {
//        //given
//        SearchOption searchOption = SearchOption.TITLE_AND_CONTENT;
//        String keyword = "1";
//        Pageable pageable = PageRequest.of(1 - 1, 10, Sort.by("boardId").descending());
//
//
//        //when
//        Page<Board> result = boardRepository.searchAll(searchOption, keyword, pageable);
//
//        //then
//        log.info("조회된 총 페이지 수={}",result.getTotalPages());
//        log.info("사이즈 ={}",result.getSize());
//        log.info("페이지 번호 ={}",result.getNumber());
//        log.info("다음 버튼 유무={}",result.hasNext());
//
//        result.getContent().forEach(board -> {
//            log.info("board={}", board);
//        });
//
//
//
//    }
//
//
//}