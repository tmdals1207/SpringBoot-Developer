package com.mysite.springbootdeveloper.repository;

import com.mysite.springbootdeveloper.domain.RefreshToken;
import com.mysite.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiredAt < :now")
    int deleteByExpiredAtBefore(@Param("now") LocalDateTime now);


}