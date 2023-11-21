package com.yzpocket.iland.entity;

public enum InfoTypeEnum {

    SHIP(Authority.SHIP),  // 항해정보
    ADS(Authority.ADS); // 광고류


    private final String authority;

    InfoTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String SHIP = "TYPE_SHIP";
        public static final String ADS = "TYPE_ADS";

    }
}