package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.Video;
import com.yzpocket.iland.entity.VideoTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoResponseDto {
    private String videoTitle;

    private String videoWriter;

    private String videoContents;

    private VideoTypeEnum videoType;

    public VideoResponseDto(Video video){
        this.videoTitle = video.getVideoTitle();
        this.videoWriter = video.getVideoWriter();
        this.videoContents = video.getVideoContents();
        this.videoType = video.getVideoType();
    }
}
