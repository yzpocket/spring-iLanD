package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.*;
import com.yzpocket.iland.service.FileService;
import com.yzpocket.iland.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/boards/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final FileService fileService;

    // 비디오 생성
    @PostMapping("/create")
    public StatusResponseDto createVideo(@ModelAttribute VideoCreateRequestDto requestDto,
                                         @RequestParam(required = false) MultipartFile file) throws IOException {
        return videoService.createVideo(requestDto, file);
    }

    // 비디오 전체 조회
    @GetMapping("/all")
    public ResponseEntity<Page<VideoResponseDto>> getAllVideos(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(videoService.getAllVideos(page, size));
    }

    // 영화 비디오만 조회
    @GetMapping("/movie")
    public ResponseEntity<Page<VideoResponseDto>> getMovieVideos() {
        Page<VideoResponseDto> movieVideos = videoService.getMovieVideos();
        return ResponseEntity.ok(movieVideos);
    }

    // TV 비디오만 조회
    @GetMapping("/tv")
    public ResponseEntity<Page<VideoResponseDto>> getTvVideos() {
        Page<VideoResponseDto> tvVideos = videoService.getTvVideos();
        return ResponseEntity.ok(tvVideos);
    }

    // 비디오 선택 조회
    @GetMapping("/{videoId}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long videoId) {
        VideoResponseDto videoResponseDto = videoService.getVideoById(videoId);

        // 파일 읽어와서 추가
        try {
            byte[] fileData = fileService.readFile(videoResponseDto.getFileUrl());
            String base64Encoded = Base64.getEncoder().encodeToString(fileData);
            videoResponseDto.setFileContent(base64Encoded);
        } catch (IOException e) {
            // 파일 읽기 실패
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(videoResponseDto);
    }

    // 비디오 수정
    @PutMapping("/update/{videoId}")
    public StatusResponseDto updateVideo(@ModelAttribute VideoUpdateRequestDto requestDto,
                                         @RequestParam(required = false) MultipartFile file,
                                         @PathVariable Long videoId) throws IOException{
        return videoService.updateVideo(requestDto, file, videoId);
    }

    // 비디오 삭제
    @DeleteMapping("/delete/{videoId}")
    public StatusResponseDto deleteVideo(@PathVariable Long videoId){
        return videoService.deleteVideo(videoId);
    }
}
