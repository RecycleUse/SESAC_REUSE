package com.sesac.reuse.repository.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.sesac.reuse.dto.board.BoardListReplyCountDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.BoardSearchOption;
import com.sesac.reuse.entity.board.QBoard;
import com.sesac.reuse.entity.reply.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> searchAll(BoardSearchOption boardSearchOption, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);

        if (boardSearchOption != null && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            switch (boardSearchOption) {
                case TITLE:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword));
                    break;
                case CONTENT:
                    booleanBuilder.and(board.content.containsIgnoreCase(keyword));
                    break;
                case NICKNAME:
                    booleanBuilder.and(board.nickname.containsIgnoreCase(keyword));
                    break;
                case TITLE_AND_CONTENT:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword)
                            .or(board.content.containsIgnoreCase(keyword))
                    );
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search option : " + boardSearchOption);

            }
            query.where(booleanBuilder);
        }
        query.where(board.boardId.gt(0L));


        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> boardList = query.fetch();
        long count = query.fetchCount();


        return new PageImpl<>(boardList, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(BoardSearchOption boardSearchOption, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        query.groupBy(board);

        if (boardSearchOption != null && keyword != null && !keyword.trim().isEmpty()) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            switch (boardSearchOption) {
                case TITLE:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword));
                    break;
                case CONTENT:
                    booleanBuilder.and(board.content.containsIgnoreCase(keyword));
                    break;
                case NICKNAME:
                    booleanBuilder.and(board.nickname.containsIgnoreCase(keyword));
                    break;
                case TITLE_AND_CONTENT:
                    booleanBuilder.and(board.title.containsIgnoreCase(keyword)
                            .or(board.content.containsIgnoreCase(keyword))
                    );
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search option : " + boardSearchOption);
            }
            query.where(booleanBuilder);
        }

        query.where(board.boardId.gt(0L));

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean(BoardListReplyCountDTO.class,
                board.boardId,
                board.title,
                board.nickname,
                board.regDate,
                board.status,
                board.type,
                reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);


    }

}

