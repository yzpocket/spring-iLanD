package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.Video;
import com.yzpocket.iland.entity.VideoTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

@Getter
@NoArgsConstructor
public class VideoResponseDto {
    private Long videoId;

    private String formattedCreatedAt; // 날짜를 문자열로 변환

    private String videoTitle;

    private String videoWriter;

    private String videoContents;

    private VideoTypeEnum videoType;

    private List<FileResponseDto> fileList; // 파일 리스트 추가

    private String fileContent;

    public VideoResponseDto(Video video){
        this.videoId = video.getVideoId();
        this.formattedCreatedAt = formatDateTime(video.getCreatedAt());
        this.videoTitle = video.getVideoTitle();
        this.videoWriter = video.getVideoWriter();
        this.videoContents = video.getVideoContents();
        this.videoType = video.getVideoType();
        // Video 엔티티에서 fileList를 가져와 FileResponseDto로 변환하여 할당
        this.fileList = FileResponseDto.fromFileList(video.getFileList());
        // 파일 리스트가 비어있지 않다면 첫 번째 파일의 내용을 Base64로 인코딩하여 할당
        this.fileContent = fileList.isEmpty() ? "" : encodeFileContent(fileList.get(0).getFileUrl());
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"); // HH시 mm분 ss초
        return dateTime.format(formatter);
    }

    public String getFileUrl() {
        // 파일 리스트가 비어있지 않다면 첫 번째 파일의 파일 URL 반환
        return fileList.isEmpty() ? "" : fileList.get(fileList.size() - 1).getFileUrl();
    }

    // 파일 내용을 Base64로 인코딩하는 메서드
    private String encodeFileContent(String filePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
