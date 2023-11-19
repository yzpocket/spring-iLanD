package com.yzpocket.iland.controller.RestController;

import com.yzpocket.iland.dto.AuthLoginRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.jwt.JwtUtil;
import com.yzpocket.iland.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 로그인
    @PostMapping("/login")
    public StatusResponseDto login(@RequestBody AuthLoginRequestDto authLoginRequestDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginRequestDto.getEmail(),
                        authLoginRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtil.createToken(userDetails.getUser().getEmail(), userDetails.getUser().getRole());
        jwtUtil.addJwtToCookie(token, response);

        return new StatusResponseDto("로그인 성공", HttpServletResponse.SC_OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public StatusResponseDto logout(HttpServletResponse response) {
        SecurityContextHolder.clearContext(); // Clear security context
        jwtUtil.removeJwtFromCookie(response); // Remove JWT from cookie
        return new StatusResponseDto("로그아웃 성공", HttpServletResponse.SC_OK);
    }
}
