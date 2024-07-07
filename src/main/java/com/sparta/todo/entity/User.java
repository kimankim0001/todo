package com.sparta.todo.entity;

import com.sparta.todo.enums.Auth;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth", nullable = false)
    private Auth auth;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken = "";

    public User(String username, String password, Auth auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
