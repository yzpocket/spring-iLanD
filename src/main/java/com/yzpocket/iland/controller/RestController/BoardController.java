package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.BoardCreateRequestDto;
import com.yzpocket.iland.dto.BoardUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 생성
    @PostMapping("/create")
    public StatusResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }

    // 게시판 수정
    @PutMapping("/update/{boardId}")
    public StatusResponseDto updateBoard(@RequestBody BoardUpdateRequestDto requestDto,
                                         @PathVariable Long boardId){
        return boardService.updateBoard(requestDto, boardId);
    }

    // 게시판 삭제
    @DeleteMapping("/delete/{boardId}")
    public StatusResponseDto deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }
}
