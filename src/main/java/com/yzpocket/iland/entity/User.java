package com.yzpocket.iland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(String email, String username, String password, UserRoleEnum role) {
        this.email=email;
        this.username=username;
        this.password=password;
        this.role=role;
    }
    public void update(String passwordE){
        this.password=passwordE;
    }
}