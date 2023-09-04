package com.sesac.reuse.entity.board;

/**
 * board 글 종류 3가지(Type)
 * 공지, 요청, 문의
 * 요청, 문의 => Status 값 지정, 공지 => Status 값 null
 */

public enum Status {
    PENDING, // 대기 (default)
    COMPLETED // 완료
}
