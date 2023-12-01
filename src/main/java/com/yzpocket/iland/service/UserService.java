package com.yzpocket.iland.service;

import com.yzpocket.iland.dto.StatusResponseDto;
import com.yzpocket.iland.dto.UserDeleteRequestDto;
import com.yzpocket.iland.dto.UserSignupRequestDto;
import com.yzpocket.iland.dto.UserUpdateRequestDto;
import com.yzpocket.iland.entity.User;
import com.yzpocket.iland.entity.UserRoleEnum;
import com.yzpocket.iland.exception.DuplicateEmailException;
import com.yzpocket.iland.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "admin";
    private final String STAFF_TOKEN = "staff";

    // 회원가입
    public StatusResponseDto signup(UserSignupRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String username = requestDto.getUsername();
            String password = passwordEncoder.encode(requestDto.getPassword1());
            UserRoleEnum role = UserRoleEnum.STAFF;
            passwordCheck(requestDto);

            emailCheck(email);

            //UserRoleEnum role = getUserRoleEnum(requestDto);

            // 사용자 등록
            User user = new User(email, username, password, role);
            userRepository.save(user);

            // 메시지와 상태 코드를 전달하여 StatusResponseDto 생성
            return new StatusResponseDto("회원가입이 완료되었습니다.", HttpStatus.OK.value());
        } catch (DuplicateEmailException e) {
            // 중복된 이메일 예외 처리
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (IllegalArgumentException e) {
            // 기타 예외 처리
            return new StatusResponseDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // 회원탈퇴
    @Transactional
    public StatusResponseDto escape(User user, UserDeleteRequestDto requestDto) {
        if (user == null) {
            return new StatusResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }

        String email = user.getEmail();

        if (!checkUserPassword(email, requestDto.getPassword1())) {
            // 비밀번호가 일치하지 않으면 에러 응답
            return new StatusResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
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
        return new StatusResponseDto("회원탈퇴가 완료되었습니다.", HttpStatus.OK.value());
    }


    // UserService 클래스 내의 update 메소드 수정
    @Transactional
    public StatusResponseDto update(User user, UserUpdateRequestDto requestDto) {
        if (user == null) {
            return new StatusResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }

        if (!requestDto.getPassword1().equals(requestDto.getPassword2())) {
            return new StatusResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        }

        User updateUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        String passwordE = passwordEncoder.encode(requestDto.getPassword1());
        updateUser.update(passwordE);

        // 메시지와 상태 코드를 전달하여 StatusResponseDto 생성
        return new StatusResponseDto("비밀번호가 변경되었습니다.", HttpStatus.OK.value());
    }

    // 모듈화 메서드
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        userRepository.delete(user);
    }
    public void passwordCheck(UserSignupRequestDto requestDto) {
        // 비밀번호1, 2 확인
        if(!requestDto.getPassword1().equals(requestDto.getPassword2())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void emailCheck(String email) {
        validateEmailFormat(email);  // 형식 검사 메소드 호출

        // email 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()){
            throw new DuplicateEmailException("중복된 email 입니다.");
        }
    }

    private void validateEmailFormat(String email) {
        // 여기서 email 형식을 검사하는 로직 추가
        if (!isValidEmailFormat(email)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private boolean isValidEmailFormat(String email) {
        // 정규식을 사용하여 email 형식 검사
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //public UserRoleEnum getUserRoleEnum(UserSignupRequestDto requestDto) {
    //    // 사용자 ROLE 확인 (미입력시 default User)
    //    UserRoleEnum role = UserRoleEnum.USER;
    //
    //    long usertype = requestDto.getUsertype();
    //    String token = requestDto.getToken();
    //
    //    if (usertype == 1) {
    //        validateToken(token, ADMIN_TOKEN, 1);
    //        role = UserRoleEnum.ADMIN;
    //    } else if (usertype == 2) {
    //        validateToken(token, STAFF_TOKEN, 2);
    //        role = UserRoleEnum.STAFF;
    //    }
    //
    //    return role;
    //}

    // 토큰 검증
    //public boolean validateToken(String inputToken, String STAFF_TOKEN, long usertype) {
    //    if (usertype == 0) {
    //        // usertype이 0인 경우에는 토큰이 없어도 정상 처리
    //        return false;
    //    }
    //
    //    if (inputToken == null || inputToken.isEmpty()) {
    //        throw new IllegalArgumentException("토큰을 입력하세요.");
    //    }
    //
    //    String userTypeString = getUserTypeString(usertype);
    //
    //    if (!this.STAFF_TOKEN.equals(inputToken) || STAFF_TOKEN.equals(inputToken)) {
    //        throw new IllegalArgumentException(userTypeString + " 토큰이 유효하지 않습니다.");
    //    }
    //    return false;
    //}

    //private String getUserTypeString(long userType) {
    //    switch ((int) userType) {
    //        case 1:
    //            return "ADMIN";
    //        case 2:
    //            return "STAFF";
    //        default:
    //            return "USER";
    //    }
    //}

    private boolean checkUserPassword(String email, String password) {
        // 이메일로 사용자 찾기
        User user = userRepository.findByEmail(email).orElse(null);

        // 사용자가 존재하고 비밀번호가 일치하면 true 반환
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}