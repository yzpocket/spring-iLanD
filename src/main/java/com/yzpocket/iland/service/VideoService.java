package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.VideCreateRequestDto;
import com.yzpocket.iland.entity.*;
import com.yzpocket.iland.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final BoardService boardService;

    // 비디오 생성
    public StatusResponseDto createVideo(VideCreateRequestDto requestDto){
        String title = requestDto.getVideoTitle();
        VideoTypeEnum type = requestDto.getVideoType();
        String writer = requestDto.getVideoWriter();
        String contents = requestDto.getVideoContents();
        Long boardId = requestDto.getBoardId();
        Board board = boardService.findBoardById(boardId);

        Video video = new Video(title, type, writer, contents, board);
        videoRepository.save(video);

        return new StatusResponseDto("영상이 생성되었습니다.", HttpStatus.OK.value());
    }
}
