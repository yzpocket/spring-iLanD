package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FileResponseDto {
    private Long fileId;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String fileUrl;


    public FileResponseDto(File file) {
        this.fileId = file.getId();
        this.fileName = file.getFileName();
        this.fileSize = file.getFileSize();
        this.fileType = file.getFileType().name();
        this.fileUrl = file.getFileUrl();
    }

    public static List<FileResponseDto> fromFileList(List<File> fileList) {
        return fileList.stream()
                .map(FileResponseDto::new)
                .collect(Collectors.toList());
    }
}