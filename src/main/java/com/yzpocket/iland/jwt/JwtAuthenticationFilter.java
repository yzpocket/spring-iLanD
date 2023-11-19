package com.yzpocket.iland.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzpocket.iland.dto.AuthLoginRequestDto;
import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.entity.UserRoleEnum;
import com.yzpocket.iland.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            AuthLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), AuthLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error("예외 발생: ", e);
            throw new RuntimeException("요청 처리 중 오류가 발생했습니다.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 생성");

        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(email, role);
        jwtUtil.addJwtToCookie(token, response);

        StatusResponseDto statusResponseDto = new StatusResponseDto("로그인 성공", HttpServletResponse.SC_OK);

        String jsonResponse = new ObjectMapper().writeValueAsString(statusResponseDto);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String failureMessage = failed.getMessage();

        StatusResponseDto statusResponseDto = new StatusResponseDto("로그인 실패: " + failureMessage, HttpServletResponse.SC_UNAUTHORIZED);

        String jsonResponse = new ObjectMapper().writeValueAsString(statusResponseDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
