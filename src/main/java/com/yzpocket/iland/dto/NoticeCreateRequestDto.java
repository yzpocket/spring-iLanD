package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.NoticeTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeCreateRequestDto {
    private String noticeTitle;

    private String noticeWriter;

    private String noticeContents;

    private NoticeTypeEnum noticeType;

    private Long boardId;
}
