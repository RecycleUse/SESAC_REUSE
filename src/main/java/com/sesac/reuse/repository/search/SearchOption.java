package com.sesac.reuse.repository.search;

public enum SearchOption {
    TITLE("Title"),
    CONTENT("Content"),
    WRITER ("Author"),
    TITLE_AND_CONTENT("Title and Content");

    private final String displayName;

    SearchOption(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
