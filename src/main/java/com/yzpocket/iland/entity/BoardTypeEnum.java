package com.yzpocket.iland.entity;

public enum BoardTypeEnum {

    NOTICE(Authority.NOTICE),  // 공지게시판
    INFO(Authority.INFO),  // 정보게시판
    CONTENTS(Authority.CONTENTS), // 컨텐츠게시판
    GAME(Authority.GAME); // 게임게시판

    private final String authority;

    BoardTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String NOTICE = "TYPE_NOTICE";
        public static final String INFO = "TYPE_INFO";
        public static final String CONTENTS = "TYPE_CONTENTS";
        public static final String GAME = "TYPE_GAME";
    }
}