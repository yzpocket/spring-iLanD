package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.NoticeCreateRequestDto;
import com.yzpocket.iland.dto.NoticeResponseDto;
import com.yzpocket.iland.dto.NoticeUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.*;
import com.yzpocket.iland.repository.FileRepository;
import com.yzpocket.iland.repository.NoticeRepository;
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
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final BoardService boardService;
    private final FileService fileService;
    private final FileRepository fileRepository;

    // 공지글 작성
    public StatusResponseDto createNotice(NoticeCreateRequestDto requestDto, MultipartFile file) throws IOException {
        String title = requestDto.getNoticeTitle();
        NoticeTypeEnum type = requestDto.getNoticeType();
        String writer = requestDto.getNoticeWriter();
        String contents = requestDto.getNoticeContents();
        Long boardId = requestDto.getBoardId();
        Board board = boardService.findBoardById(boardId);

        // UUID 생성
        String noticeUUID = UUID.randomUUID().toString();

        Notice notice = new Notice(title, type, writer, contents, board);
        noticeRepository.save(notice);

        // 파일이 선택된 경우에만 파일 처리
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            // UUID와 파일명 조합
            String uniqueFileName = noticeUUID + "_" + originalFileName;
            String filePath = fileService.uploadFile(file, uniqueFileName);

            // 파일 정보를 Notice 엔티티에 추가
            File fileEntity = new File(uniqueFileName, file.getSize(), FileTypeEnum.IMG, filePath);
            notice.addFile(fileEntity);

            // 파일 엔티티 저장
            fileRepository.save(fileEntity);
        }

        return new StatusResponseDto("공지글이 생성되었습니다.", HttpStatus.OK.value());
    }


    // 공지글 전체 조회 + 페이징
    public Page<NoticeResponseDto> getAllNotices(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "noticeId");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Notice> noticeList = noticeRepository.findAll(pageable);
        return noticeList.map(NoticeResponseDto::new);
    }
    // 공지글 전체 조회 + 페이징
    public Page<NoticeResponseDto> getNormalNotices(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "noticeId");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Notice> noticeList = noticeRepository.findByNoticeTypeAndBoardNotNull(NoticeTypeEnum.NORMAL, pageable);
        return noticeList.map(NoticeResponseDto::new);
    }
    // 중요 공지 조회
    public Page<NoticeResponseDto> getImportantNotices() {
        Page<Notice> importantNoticeList = noticeRepository.findByNoticeTypeAndBoardNotNullOrderByNoticeIdDesc(NoticeTypeEnum.IMPORTANT, Pageable.unpaged());
        return importantNoticeList.map(NoticeResponseDto::new);
    }

    // 공지글 선택 조회
    public NoticeResponseDto getNoticeById(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                ()->new NullPointerException("선택한 공지가 없습니다."));
        return new NoticeResponseDto(notice);
    }

    // 공지글 수정
    @Transactional
    public StatusResponseDto updateNotice(NoticeUpdateRequestDto requestDto, Long noticeId) {
        Notice updateNotice = findNoticeById(noticeId);
        updateNotice.update(requestDto);

        return new StatusResponseDto("공지글이 수정되었습니다.", HttpStatus.OK.value());
    }

    // 공지글 삭제
    @Transactional
    public StatusResponseDto deleteNotice(Long noticeId) {
        Notice deleteNotice = findNoticeById(noticeId);
        noticeRepository.delete(deleteNotice);

        return new StatusResponseDto("공지글이 삭제되었습니다.", HttpStatus.OK.value());
    }

    // 공통으로 사용할 공지글 찾기 메서드
    public Notice findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException("공지글을 찾을 수 없습니다. noticeId: " + noticeId));
    }
}
