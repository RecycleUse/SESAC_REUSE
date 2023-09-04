package com.sesac.reuse.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

//QuerydslRepositorySupport 클래스는
// getQuerydsl() : Querydsl의 SQLQueryFactory 객체를 반환, 동줙 쿼리 생성시 사용
// applyPagination() : 페이징, 정렬 기능을 적용해서 querydsl 쿼리 작성에 도움을 줌
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    /*Pageable 타입의 객체를 파라미터로 전달 => 페이징 처리
    PageRequest.of(...) 를 통해 Pageable 타입의 구현체 생성
    PageRequest.of(페이지번호,사이즈,Sort) :가변인자
    예) 목록을 조회할 때, 조건을 줘서 페이징 처리하는거니까 findAll(Pageable 타입 객체) => Page<T> 객체 리턴 => 출력데이터DTO로 변환 후 리턴
    JpaRepository에서는 findAll() 을 통해 기본적인 페이징 처리 지원

    반환되는 Page<T> 객체에는, 데이터 총 갯수 (getTotalElements)<--그래서 전체갯수 count쿼리도 별도로 발생, 해당 페이지 데이터(getContent) 등 다양한 기능 지원
    */
    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board); //QEntity를 통해 JPQL생성
        query.where(board.title.contains("1"));

//        List<Board> list = query.fetch();//작성된 JPQL실행 <-- 이 상태로 쿼리 실행시 모든 데이터 가져옴
//        long count = query.fetchCount(); //쿼리 일치해서 반환된 row 수

        //paging처리 (여기서 this로 getQuerydsl() 수행 가능한 이유는 extends QuerydslRepositorySupport 해서
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();//<-- 이제 paging처리 적용
        long count = query.fetchCount();


        return null;
    }

    @Override
    public Page<Board> searchAll(SearchOption searchOption, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if (searchOption != null && keyword != null) { //검색 옵션 사용하는 경우
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            switch (searchOption) {
                case TITLE:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword));
                    break;
                case CONTENT:
                    booleanBuilder.and(board.content.containsIgnoreCase(keyword));
                    break;
                case WRITER:
                    booleanBuilder.and(board.writer.containsIgnoreCase(keyword));
                    break;
                case TITLE_AND_CONTENT:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword)
                            .or(board.content.containsIgnoreCase(keyword))
                    );
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search option : " + searchOption);

            }
            query.where(booleanBuilder);
        }
        query.where(board.boardId.gt(0L));

        //페이징처리
        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> boardList = query.fetch();
        long count = query.fetchCount(); //조회된 전체 데이터 수

        //Page<T> 타입을 반환해줘야함
        return new PageImpl<>(boardList,pageable,count);

    }
}
