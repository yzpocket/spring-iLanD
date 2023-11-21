package com.yzpocket.iland.entity;

public enum FileTypeEnum {

    IMG(Authority.IMG),  // 이미지
    VIDEO(Authority.VIDEO); // 영상


    private final String authority;

    FileTypeEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String IMG = "TYPE_IMG";
        public static final String VIDEO = "TYPE_VIDEO";

    }
}