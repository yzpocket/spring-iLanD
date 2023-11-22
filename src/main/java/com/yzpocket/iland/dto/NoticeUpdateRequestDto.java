package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.NoticeTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeUpdateRequestDto {
    private String noticeTitle;

    private String noticeWriter;

    private String noticeContents;

    private NoticeTypeEnum noticeType;
}
