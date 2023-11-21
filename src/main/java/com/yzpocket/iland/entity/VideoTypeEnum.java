package com.yzpocket.iland.entity;

public enum VideoTypeEnum {

    MOVIE(Authority.MOVIE),  // 영화
    TV(Authority.TV); // TV프로그램


    private final String authority;

    VideoTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MOVIE = "TYPE_MOVIE";
        public static final String TV = "TYPE_TV";

    }
}