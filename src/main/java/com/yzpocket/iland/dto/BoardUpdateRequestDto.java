package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.BoardTypeEnum;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class BoardUpdateRequestDto {

    private String title;

    private BoardTypeEnum type;
}
