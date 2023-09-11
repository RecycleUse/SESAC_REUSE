package com.sesac.reuse.entity.board;

public enum Type {
    NOTICE("공지사항"),
    ADDITIONAL_REQUEST("추가요청"),
    INQUIRY_ETC("기타문의");

    private String displayTypeName;

    Type(String displayName) {
        this.displayTypeName = displayName;
    }

    public String getDisplayTypeName() {
        return displayTypeName;
    }
}
