package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.NoticeCreateRequestDto;
import com.yzpocket.iland.dto.NoticeUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.NoticeService;
import lombok.RequiredArgsConstructor;
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
