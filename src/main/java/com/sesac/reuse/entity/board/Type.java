package com.sesac.reuse.entity.board;

/**
 * 게시글 종류:
 * 공지(ADMIN만 선택 가능), 요청, 문의
 */
public enum Type {
    ANNOUNCEMENT, // 공지사항
    ADDITIONAL_REQUEST, // 추가요청
    INQUIRY_ETC // 문의기타
}
