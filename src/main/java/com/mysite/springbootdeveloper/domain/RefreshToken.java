package com.mysite.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;


    public RefreshToken(User user, String refreshToken, LocalDateTime expiredAt) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public RefreshToken update(String newRefreshToken, LocalDateTime newExpiredAt) {
        this.refreshToken = newRefreshToken;
        this.expiredAt = newExpiredAt;
        return this;
    }
}