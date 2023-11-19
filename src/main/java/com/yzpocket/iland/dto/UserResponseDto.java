package com.yzpocket.iland.dto;

import com.yzpocket.iland.entity.User;
import com.yzpocket.iland.entity.UserRoleEnum;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private UserRoleEnum role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
