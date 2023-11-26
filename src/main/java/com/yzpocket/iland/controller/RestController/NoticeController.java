package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.NoticeCreateRequestDto;
import com.yzpocket.iland.dto.NoticeResponseDto;
import com.yzpocket.iland.dto.NoticeUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    // 공지글 생성
    @PostMapping("/create")
    public StatusResponseDto createNotice(@RequestBody NoticeCreateRequestDto requestDto){
        return noticeService.createNotice(requestDto);
    }

    // 공지글 전체 조회
    @GetMapping("/all")
    public ResponseEntity<Page<NoticeResponseDto>> getAllNotices(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(noticeService.getAllNotices(page, size));
    }

    // 공지글 선택 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponseDto> getNoticeById(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.getNoticeById(noticeId));
    }

    // 중요공지만 조회
    @GetMapping("/important")
    public ResponseEntity<Page<NoticeResponseDto>> getImportantNotices() {
        Page<NoticeResponseDto> importantNotices = noticeService.getImportantNotices();
        return ResponseEntity.ok(importantNotices);
    }

    // 공지글 수정
    @PutMapping("/update/{noticeId}")
    public StatusResponseDto updateNotice(@RequestBody NoticeUpdateRequestDto requestDto,
                                          @PathVariable Long noticeId){
        return noticeService.updateNotice(requestDto, noticeId);
    }

    // 공지글 삭제
    @DeleteMapping("/delete/{noticeId}")
    public StatusResponseDto deleteNotice(@PathVariable Long noticeId){
        return noticeService.deleteNotice(noticeId);
    }
}
