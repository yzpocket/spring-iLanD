package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.VideoTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideCreateRequestDto {
    private String videoTitle;

    private String videoWriter;

    private String videoContents;

    private VideoTypeEnum videoType;

    private Long boardId;
}
