package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.Notice;
import com.yzpocket.iland.entity.NoticeTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;

    private String formattedCreatedAt; // 날짜를 문자열로 변환

    private String noticeTitle;

    private String noticeWriter;

    private String noticeContents;

    private NoticeTypeEnum noticeType;

    public NoticeResponseDto(Notice notice){
        this.noticeId = notice.getNoticeId();
        this.formattedCreatedAt = formatDateTime(notice.getCreatedAt());
        this.noticeTitle = notice.getNoticeTitle();
        this.noticeWriter = notice.getNoticeWriter();
        this.noticeContents = notice.getNoticeContents();
        this.noticeType = notice.getNoticeType();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"); // HH시 mm분 ss초
        return dateTime.format(formatter);
    }
}
