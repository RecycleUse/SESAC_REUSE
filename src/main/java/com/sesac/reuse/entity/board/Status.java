package com.sesac.reuse.entity.board;

public enum Status {
    PENDING("처리중"),
    COMPLETED("완료");

    private String displayStatusName;

    Status(String displayStatusName) {
        this.displayStatusName = displayStatusName;
    }

    public String getDisplayStatusName() {
        return displayStatusName;
    }
}