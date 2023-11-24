package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.InfoTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoCreateReqeustDto {
    private String infoTitle;

    private String infoWriter;

    private String infoContents;

    private InfoTypeEnum infoType;

    private Long boardId;
}
