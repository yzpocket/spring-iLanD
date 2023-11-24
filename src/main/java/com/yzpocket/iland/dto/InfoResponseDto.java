package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.Info;
import com.yzpocket.iland.entity.InfoTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoResponseDto {
    private String infoTitle;

    private String infoWriter;

    private String infoContents;

    private InfoTypeEnum infoType;

    public InfoResponseDto(Info info){
        this.infoTitle = info.getInfoTitle();
        this.infoWriter = info.getInfoWriter();
        this.infoContents = info.getInfoContents();
        this.infoType = info.getInfoType();
    }
}
