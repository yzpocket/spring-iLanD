package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.VideCreateRequestDto;
import com.yzpocket.iland.dto.VideoResponseDto;
import com.yzpocket.iland.dto.VideoUpdateRequestDto;
import com.yzpocket.iland.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 비디오 전체 조회
    @GetMapping("/all")
    public ResponseEntity<Page<VideoResponseDto>> getAllVideos(@RequestParam("page") int page) {
        return ResponseEntity.ok(videoService.getAllVideos(page - 1));
    }

    // 비디오 선택 조회
    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long videoId) {
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }

    // 비디오 수정
    @PutMapping("/update/{videoId}")
    public StatusResponseDto updatevideo(@RequestBody VideoUpdateRequestDto requestDto,
                                         @PathVariable Long videoId){
        return videoService.updateVideo(requestDto, videoId);
    }

    // 비디오 삭제
    @DeleteMapping("/delete/{videoId}")
    public StatusResponseDto deleteVideo(@PathVariable Long videoId){
        return videoService.deleteVideo(videoId);
    }
}
