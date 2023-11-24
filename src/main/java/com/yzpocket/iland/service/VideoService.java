package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.VideCreateRequestDto;
import com.yzpocket.iland.dto.VideoResponseDto;
import com.yzpocket.iland.dto.VideoUpdateRequestDto;
import com.yzpocket.iland.entity.Board;
import com.yzpocket.iland.entity.Video;
import com.yzpocket.iland.entity.VideoTypeEnum;
import com.yzpocket.iland.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 비디오 전체 조회 + 페이징
    public Page<VideoResponseDto> getAllVideos(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "videoId");
        Pageable pageable = PageRequest.of(page, 20, sort);

        Page<Video> videoList = videoRepository.findAll(pageable);
        return videoList.map(VideoResponseDto::new);
    }

    // 비디오 선택 조회
    public VideoResponseDto getVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(
                ()->new NullPointerException("선택한 영상이 없습니다."));
        return new VideoResponseDto(video);
    }

    // 비디오 수정
    @Transactional
    public StatusResponseDto updateVideo(VideoUpdateRequestDto requestDto, Long videoId) {
        Video updateVideo = findVideoById(videoId);
        updateVideo.update(requestDto);

        return new StatusResponseDto("영상이 수정되었습니다.", HttpStatus.OK.value());
    }

    // 비디오 삭제
    @Transactional
    public StatusResponseDto deleteVideo(Long videoId) {
        Video deleteVideo = findVideoById(videoId);
        videoRepository.delete(deleteVideo);

        return new StatusResponseDto("영상이 삭제되었습니다.", HttpStatus.OK.value());
    }

    // 공통으로 사용할 공지글 찾기 메서드
    public Video findVideoById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("영상을 찾을 수 없습니다. videoId: " + videoId));
    }
}
