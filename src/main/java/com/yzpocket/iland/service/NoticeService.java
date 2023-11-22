package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.NoticeCreateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.Board;
import com.yzpocket.iland.entity.Notice;
import com.yzpocket.iland.entity.NoticeTypeEnum;
import com.yzpocket.iland.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final BoardService boardService;

    // 공지글 작성
    public StatusResponseDto createNotice(NoticeCreateRequestDto requestDto) {
        String title = requestDto.getNoticeTitle();
        NoticeTypeEnum type = requestDto.getNoticeType();
        String writer = requestDto.getNoticeWriter();
        String contents = requestDto.getNoticeContents();
        Long boardId = requestDto.getBoardId();
        Board board = boardService.findBoardById(boardId);

        Notice notice = new Notice(title, type, writer, contents, board);
        noticeRepository.save(notice);

        return new StatusResponseDto("공지글이 생성되었습니다.", HttpStatus.OK.value());
    }
}
