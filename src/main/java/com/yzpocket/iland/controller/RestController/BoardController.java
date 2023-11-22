package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.BoardCreateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public StatusResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }
}
