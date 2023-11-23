package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.VideCreateRequestDto;
import com.yzpocket.iland.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    // 비디오 생성
    @PostMapping("/create")
    public StatusResponseDto createVideo(@RequestBody VideCreateRequestDto requestDto){
        return videoService.createVideo(requestDto);
    }
}
