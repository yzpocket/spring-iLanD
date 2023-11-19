package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.UserDeleteRequestDto;
import com.yzpocket.iland.dto.UserSignupRequestDto;
import com.yzpocket.iland.dto.UserUpdateRequestDto;
import com.yzpocket.iland.entity.User;
import com.yzpocket.iland.entity.UserRoleEnum;
import com.yzpocket.iland.exception.DuplicateEmailException;
//import com.yzpocket.iland.jwt.SecurityUtil;
import com.yzpocket.iland.repository.UserRepository;
import com.yzpocket.iland.security.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "admin";
    private final String STAFF_TOKEN = "staff";

    public ResponseEntity<StatusResponseDto> signup(UserSignupRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String username = requestDto.getUsername();
            String password = passwordEncoder.encode(requestDto.getPassword1());

            passwordCheck(requestDto);

            emailCheck(email);

            UserRoleEnum role = getUserRoleEnum(requestDto);

            // 사용자 등록
            User user = new User(email, username, password, role);
            userRepository.save(user);

            // 메시지와 상태 코드를 전달하여 StatusResponseDto 생성
            StatusResponseDto responseDto = new StatusResponseDto("회원가입이 완료되었습니다.", HttpStatus.OK.value());

            // ResponseEntity에 StatusResponseDto를 담아 반환
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (DuplicateEmailException e) {
            // 중복된 이메일 예외 처리
            StatusResponseDto errorResponseDto = new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            // 기타 예외 처리
            StatusResponseDto errorResponseDto = new StatusResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<StatusResponseDto> escape(User user, UserDeleteRequestDto requestDto) {
        // 현재 로그인한 사용자의 이메일 가져오기
        String email = user.getEmail();

        // 사용자 비밀번호 확인
        if (!checkUserPassword(email, requestDto.getPassword1())) {
            // 비밀번호가 일치하지 않으면 에러 응답
            StatusResponseDto errorResponseDto = new StatusResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
        }

        // 비밀번호가 일치하면 사용자 삭제
        deleteUser(email);

        // 쿠키 삭제
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 회원탈퇴 완료 메시지 응답
        StatusResponseDto responseDto = new StatusResponseDto("회원탈퇴가 완료되었습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        userRepository.delete(user);
    }

    @Transactional
    public StatusResponseDto update(User user, UserUpdateRequestDto requestDto) {
        if(!requestDto.getPassword1().equals(requestDto.getPassword2())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User updateUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        String passwordE = passwordEncoder.encode(requestDto.getPassword1());
        updateUser.update(passwordE);

        // 메시지와 상태 코드를 전달하여 StatusResponseDto 생성
        return new StatusResponseDto("비밀번호가 변경되었습니다.", HttpStatus.OK.value());
    }

    public void passwordCheck(UserSignupRequestDto requestDto) {
        // 비밀번호1, 2 확인
        if(!requestDto.getPassword1().equals(requestDto.getPassword2())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void emailCheck(String email) {
        // email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()){
            throw new DuplicateEmailException("중복된 email 입니다.");
        }
    }

    public UserRoleEnum getUserRoleEnum(UserSignupRequestDto requestDto) {
        // 사용자 ROLE 확인 (미입력시 default User)
        UserRoleEnum role = UserRoleEnum.USER;

        long userType = requestDto.getUsertype();
        String token = requestDto.getToken();

        if (userType == 1) {
            validateToken(token, ADMIN_TOKEN, 1);
            role = UserRoleEnum.ADMIN;
        } else if (userType == 2) {
            validateToken(token, STAFF_TOKEN, 2);
            role = UserRoleEnum.STAFF;
        }

        return role;
    }

    private void validateToken(String inputToken, String expectedToken, long userType) {
        if (userType == 0) {
            // usertype이 0인 경우에는 토큰이 없어도 정상 처리
            return;
        }

        if (inputToken == null || inputToken.isEmpty()) {
            throw new IllegalArgumentException("토큰을 입력하세요.");
        }

        String userTypeString = (userType == 1) ? "ADMIN" : "STAFF";
        if (!expectedToken.equals(inputToken)) {
            throw new IllegalArgumentException(userTypeString + " 토큰이 유효하지 않습니다.");
        }
    }
    private boolean checkUserPassword(String email, String password) {
        // 이메일로 사용자 찾기
        User user = userRepository.findByEmail(email).orElse(null);

        // 사용자가 존재하고 비밀번호가 일치하면 true 반환
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}