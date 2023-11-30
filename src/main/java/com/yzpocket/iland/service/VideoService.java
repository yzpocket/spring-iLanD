package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.*;
import com.yzpocket.iland.entity.*;
import com.yzpocket.iland.repository.FileRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final BoardService boardService;
    private final FileService fileService;
    private final FileRepository fileRepository;

    // 비디오 생성
    public StatusResponseDto createVideo(VideoCreateRequestDto requestDto, MultipartFile file) throws IOException, IOException {
        String title = requestDto.getVideoTitle();
        VideoTypeEnum type = requestDto.getVideoType();
        String writer = requestDto.getVideoWriter();
        String contents = requestDto.getVideoContents();
        Long boardId = requestDto.getBoardId();
        Board board = boardService.findBoardById(boardId);

        // UUID 생성
        String videoUUID = UUID.randomUUID().toString();

        Video video = new Video(title, type, writer, contents, board);
        videoRepository.save(video);

        // 파일이 선택된 경우에만 파일 처리
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            // UUID와 파일명 조합
            String uniqueFileName = videoUUID + "_" + originalFileName;
            String filePath = fileService.uploadFile(file, uniqueFileName);

            // 파일 정보를 Notice 엔티티에 추가
            File fileEntity = new File(uniqueFileName, file.getSize(), FileTypeEnum.IMG, filePath);
            video.addFile(fileEntity);

            // 파일 엔티티 저장
            fileRepository.save(fileEntity);
        }

        return new StatusResponseDto("영상이 생성되었습니다.", HttpStatus.OK.value());
    }

    // 비디오 전체 조회 + 페이징
    public Page<VideoResponseDto> getAllVideos(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "videoId");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Video> videoList = videoRepository.findAll(pageable);
        return videoList.map(VideoResponseDto::new);
    }

    // 영화 비디오 조회
    public Page<VideoResponseDto> getMovieVideos() {
        Page<Video> movieVideoList = videoRepository.findByVideoTypeAndBoardNotNullOrderByVideoIdDesc(VideoTypeEnum.MOVIE, Pageable.unpaged());
        return movieVideoList.map(VideoResponseDto::new);
    }

    // TV 비디오 조회
    public Page<VideoResponseDto> getTvVideos() {
        Page<Video> tvVideoList = videoRepository.findByVideoTypeAndBoardNotNullOrderByVideoIdDesc(VideoTypeEnum.TV, Pageable.unpaged());
        return tvVideoList.map(VideoResponseDto::new);
    }

    // 비디오 선택 조회
    public VideoResponseDto getVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(
                ()->new NullPointerException("선택한 영상이 없습니다."));
        return new VideoResponseDto(video);
    }

    // 비디오 수정
    @Transactional
    public StatusResponseDto updateVideo(VideoUpdateRequestDto requestDto, MultipartFile newFile, Long videoId) throws IOException {
        Video updateVideo = findVideoById(videoId);
        updateVideo.update(requestDto);

        // UUID 생성
        String videoUUID = UUID.randomUUID().toString();

        // 파일이 선택된 경우에만 파일 처리
        if (newFile != null) {
            String originalFileName = newFile.getOriginalFilename();
            // UUID와 파일명 조합
            String uniqueFileName = videoUUID + "_" + originalFileName;
            String filePath = fileService.uploadFile(newFile, uniqueFileName);

            // 파일 정보를 Video 엔티티에 추가
            File fileEntity = new File(uniqueFileName, newFile.getSize(), FileTypeEnum.IMG, filePath);
            updateVideo.addFile(fileEntity);

            // 파일 엔티티 저장
            fileRepository.save(fileEntity);
        }

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
