package com.yzpocket.iland.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDeleteRequestDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{8,15}+$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자를 사용하세요.")
    private String password1;
}
