package com.yzpocket.iland.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 파일 업로드 및 저장 로직
    public String uploadFile(MultipartFile file, String uniqueFileName) throws IOException {
        // 파일명 보호를 위해서 UUID 포함된 파일명 사용
        String fullPath = uploadDir + uniqueFileName;

        // 파일 저장
        Files.copy(file.getInputStream(), Path.of(fullPath), StandardCopyOption.REPLACE_EXISTING);

        // 저장된 파일의 경로나 파일명을 반환
        return fullPath;
    }
}