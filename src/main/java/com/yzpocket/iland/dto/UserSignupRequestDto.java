package com.yzpocket.iland.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
public class UserSignupRequestDto {

    @Email
    @NotBlank
    private String email;

    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{4,10}", message = "아이디 4~10자 영문 소문자, 숫자를 사용하세요.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름은 한글로 2글자에서 4글자까지 입력하세요.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,15}+$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password1;

    private String password2;

    private long usertype;

    private String token;
}
