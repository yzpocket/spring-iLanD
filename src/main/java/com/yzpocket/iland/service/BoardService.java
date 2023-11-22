package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.BoardCreateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.Board;
import com.yzpocket.iland.entity.BoardTypeEnum;
import com.yzpocket.iland.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public StatusResponseDto createBoard(BoardCreateRequestDto requestDto) {
        String title = requestDto.getTitle();
        BoardTypeEnum type = requestDto.getType();
        Board board = new Board(title, type);
        boardRepository.save(board);

        return new StatusResponseDto("게시판이 생성되었습니다.", HttpStatus.OK.value());
    }
}
