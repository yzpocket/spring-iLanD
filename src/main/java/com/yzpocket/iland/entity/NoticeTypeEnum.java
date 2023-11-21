package com.yzpocket.iland.entity;

public enum NoticeTypeEnum {

    IMPORTANT(Authority.IMPORTANT),  // 중요공지
    NONE(Authority.NONE); // 일반공지


    private final String authority;

    NoticeTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String IMPORTANT = "TYPE_IMPORTANT";
        public static final String NONE = "TYPE_NONE";

    }
}