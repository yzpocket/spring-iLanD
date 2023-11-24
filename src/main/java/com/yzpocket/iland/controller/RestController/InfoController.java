package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.InfoCreateReqeustDto;
import com.yzpocket.iland.dto.InfoResponseDto;
import com.yzpocket.iland.dto.InfoUpdateRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    // 비디오 생성
    @PostMapping("/create")
    public StatusResponseDto createinfo(@RequestBody InfoCreateReqeustDto requestDto){
        return infoService.createInfo(requestDto);
    }

    // 비디오 전체 조회
    @GetMapping("/all")
    public ResponseEntity<Page<InfoResponseDto>> getAllInfos(@RequestParam("page") int page) {
        return ResponseEntity.ok(infoService.getAllInfos(page - 1));
    }

    // 비디오 선택 조회
    @GetMapping("/{infoId}")
    public ResponseEntity<InfoResponseDto> getInfoById(@PathVariable Long infoId) {
        return ResponseEntity.ok(infoService.getInfoById(infoId));
    }

    // 비디오 수정
    @PutMapping("/update/{infoId}")
    public StatusResponseDto updateInfo(@RequestBody InfoUpdateRequestDto requestDto,
                                         @PathVariable Long infoId){
        return infoService.updateInfo(requestDto, infoId);
    }

    // 비디오 삭제
    @DeleteMapping("/delete/{infoId}")
    public StatusResponseDto deleteInfo(@PathVariable Long infoId){
        return infoService.deleteInfo(infoId);
    }
}
