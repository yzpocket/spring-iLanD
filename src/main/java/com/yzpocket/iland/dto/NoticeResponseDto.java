package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.Notice;
import com.yzpocket.iland.entity.NoticeTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
    private String noticeTitle;

    private String noticeWriter;

    private String noticeContents;

    private NoticeTypeEnum noticeType;

    public NoticeResponseDto(Notice notice){
        this.noticeTitle = notice.getNoticeTitle();
        this.noticeWriter = notice.getNoticeWriter();
        this.noticeContents = notice.getNoticeContents();
        this.noticeType = notice.getNoticeType();
    }
}
