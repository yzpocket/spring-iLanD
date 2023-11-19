package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.UserDeleteRequestDto;
import com.yzpocket.iland.dto.UserSignupRequestDto;
import com.yzpocket.iland.dto.UserUpdateRequestDto;
import com.yzpocket.iland.security.UserDetailsImpl;
import com.yzpocket.iland.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public StatusResponseDto signup(@Valid @RequestBody UserSignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    // 회원탈퇴
    @DeleteMapping("/escape")
    public StatusResponseDto escape(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody UserDeleteRequestDto requestDto) {
        if (userDetails == null) {
            return new StatusResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }
        return userService.escape(userDetails.getUser(), requestDto);
    }

    // 회원수정
    @PutMapping("/update")
    public StatusResponseDto update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody UserUpdateRequestDto requestDto){
        if (userDetails == null) {
            return new StatusResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }
        return userService.update(userDetails.getUser(), requestDto);
    }
}