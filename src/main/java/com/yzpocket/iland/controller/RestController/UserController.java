package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.UserDeleteRequestDto;
import com.yzpocket.iland.dto.UserSignupRequestDto;
import com.yzpocket.iland.dto.UserUpdateRequestDto;
import com.yzpocket.iland.security.UserDetailsImpl;
import com.yzpocket.iland.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StatusResponseDto> signup(@Valid @RequestBody UserSignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @DeleteMapping("/escape")
    public ResponseEntity<StatusResponseDto> escape(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @Valid @RequestBody UserDeleteRequestDto requestDto) {
        ResponseEntity<StatusResponseDto> escapeResult = userService.escape(userDetails.getUser(), requestDto);
        return escapeResult;
    }

    @PutMapping("/update")
    public ResponseEntity<StatusResponseDto> update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                    @Valid @RequestBody UserUpdateRequestDto requestDto){
        return ResponseEntity.ok().body(userService.update(userDetails.getUser(), requestDto));
    }
}