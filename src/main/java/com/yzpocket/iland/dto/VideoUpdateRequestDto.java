package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.VideoTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoUpdateRequestDto {
    private String videoTitle;

    private String videoWriter;

    private String videoContents;

    private VideoTypeEnum videoType;
}
