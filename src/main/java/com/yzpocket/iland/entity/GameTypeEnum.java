package com.yzpocket.iland.entity;

public enum GameTypeEnum {

    PAINT(Authority.PAINT),  // 그리기
    ETC(Authority.ETC); // 기타


    private final String authority;

    GameTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String PAINT = "TYPE_PAINT";
        public static final String ETC = "TYPE_ETC";

    }
}