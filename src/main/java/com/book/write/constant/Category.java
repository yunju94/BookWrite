package com.book.write.constant;

public enum Category {
    Fantasy("판타지"),
    RomanceFantasy("로맨스 판타지"),

    Romance("로맨스"),
    etc("기타");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
