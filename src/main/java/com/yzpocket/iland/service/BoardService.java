package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.BoardCreateRequestDto;
import com.yzpocket.iland.dto.BoardUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.Board;
import com.yzpocket.iland.entity.BoardTypeEnum;
import com.yzpocket.iland.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시판 생성
    public StatusResponseDto createBoard(BoardCreateRequestDto requestDto) {
        String title = requestDto.getTitle();
        BoardTypeEnum type = requestDto.getType();
        Board board = new Board(title, type);
        boardRepository.save(board);

        return new StatusResponseDto("게시판이 생성되었습니다.", HttpStatus.OK.value());
    }

    // 게시판 수정
    @Transactional
    public StatusResponseDto updateBoard(BoardUpdateRequestDto requestDto, Long boardId) {
        Board updateBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다.")
        );
        updateBoard.update(requestDto);

        return new StatusResponseDto("게시판이 수정되었습니다.", HttpStatus.OK.value());
    }
}
