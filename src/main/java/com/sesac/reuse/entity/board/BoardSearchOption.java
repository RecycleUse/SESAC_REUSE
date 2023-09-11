package com.sesac.reuse.entity.board;

public enum BoardSearchOption {

    TITLE("제목"),
    CONTENT("내용"),
    NICKNAME("작성자"),
    TITLE_AND_CONTENT("제목+내용");

    private final String displayName;

    BoardSearchOption(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
