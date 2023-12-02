package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.NoticeCreateRequestDto;
import com.yzpocket.iland.dto.NoticeResponseDto;
import com.yzpocket.iland.dto.NoticeUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.FileService;
import com.yzpocket.iland.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/boards/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final FileService fileService;

    // 공지글 생성
    @PostMapping("/create")
    public StatusResponseDto createNotice(@ModelAttribute NoticeCreateRequestDto requestDto,
                                          @RequestParam(required = false) MultipartFile file) throws IOException {
        return noticeService.createNotice(requestDto, file);
    }

    // 공지글 전체 조회
    @GetMapping("/all")
    public ResponseEntity<Page<NoticeResponseDto>> getAllNotices(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(noticeService.getAllNotices(page, size));
    }

    // 일반 공지글 조회
    @GetMapping("/normal")
    public ResponseEntity<Page<NoticeResponseDto>> getNormalNotices(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(noticeService.getNormalNotices(page, size));
    }

    // 공지글 선택 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long noticeId) {
        NoticeResponseDto noticeResponseDto = noticeService.getNoticeById(noticeId);

        // 파일이 있는 경우에만 파일 읽어와서 추가
        if (noticeResponseDto.getFileUrl() != null && !noticeResponseDto.getFileUrl().isEmpty()) {
            try {
                byte[] fileData = fileService.readFile(noticeResponseDto.getFileUrl());
                String base64Encoded = Base64.getEncoder().encodeToString(fileData);
                noticeResponseDto.setFileContent(base64Encoded);
            } catch (IOException e) {
                // 파일 읽기 실패
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.ok(noticeResponseDto);
    }

    // 중요공지만 조회
    @GetMapping("/important")
    public ResponseEntity<Page<NoticeResponseDto>> getImportantNotices() {
        Page<NoticeResponseDto> importantNotices = noticeService.getImportantNotices();
        return ResponseEntity.ok(importantNotices);
    }

    // 공지글 수정
    @PutMapping("/update/{noticeId}")
    public StatusResponseDto updateNotice(@ModelAttribute NoticeUpdateRequestDto requestDto,
                                          @PathVariable Long noticeId,
                                          @RequestParam(required = false) MultipartFile file) throws IOException {
        return noticeService.updateNotice(requestDto, noticeId, file);
    }

    // 공지글 삭제
    @DeleteMapping("/delete/{noticeId}")
    public StatusResponseDto deleteNotice(@PathVariable Long noticeId){
        return noticeService.deleteNotice(noticeId);
    }
}
