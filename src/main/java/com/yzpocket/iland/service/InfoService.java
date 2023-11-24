package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.InfoCreateReqeustDto;
import com.yzpocket.iland.dto.InfoResponseDto;
import com.yzpocket.iland.dto.InfoUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.Board;
import com.yzpocket.iland.entity.Info;
import com.yzpocket.iland.entity.InfoTypeEnum;
import com.yzpocket.iland.repository.InfoRepository;
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
public class InfoService {
    private final InfoRepository infoRepository;
    private final BoardService boardService;

    // 정보글 생성
    public StatusResponseDto createInfo(InfoCreateReqeustDto requestDto){
        String title = requestDto.getInfoTitle();
        InfoTypeEnum type = requestDto.getInfoType();
        String writer = requestDto.getInfoWriter();
        String contents = requestDto.getInfoContents();
        Long boardId = requestDto.getBoardId();
        Board board = boardService.findBoardById(boardId);

        Info info = new Info(title, type, writer, contents, board);
        infoRepository.save(info);

        return new StatusResponseDto("정보글이 생성되었습니다.", HttpStatus.OK.value());
    }

    // 정보글 전체 조회 + 페이징
    public Page<InfoResponseDto> getAllInfos(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "infoId");
        Pageable pageable = PageRequest.of(page, 20, sort);

        Page<Info> infoList = infoRepository.findAll(pageable);
        return infoList.map(InfoResponseDto::new);
    }

    // 정보글 선택 조회
    public InfoResponseDto getInfoById(Long infoId) {
        Info info = infoRepository.findById(infoId).orElseThrow(
                ()->new NullPointerException("선택한 정보글이 없습니다."));
        return new InfoResponseDto(info);
    }

    // 정보글 수정
    @Transactional
    public StatusResponseDto updateInfo(InfoUpdateRequestDto requestDto, Long infoId) {
        Info updateInfo = findInfoById(infoId);
        updateInfo.update(requestDto);

        return new StatusResponseDto("정보글이 수정되었습니다.", HttpStatus.OK.value());
    }

    // 정보글 삭제
    @Transactional
    public StatusResponseDto deleteInfo(Long infoId) {
        Info deleteInfo = findInfoById(infoId);
        infoRepository.delete(deleteInfo);

        return new StatusResponseDto("정보글이 삭제되었습니다.", HttpStatus.OK.value());
    }

    // 공통으로 사용할 공지글 찾기 메서드
    public Info findInfoById(Long infoId) {
        return infoRepository.findById(infoId)
                .orElseThrow(() -> new EntityNotFoundException("정보글을 찾을 수 없습니다. infoId: " + infoId));
    }
}
