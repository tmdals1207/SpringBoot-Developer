package com.mysite.springbootdeveloper.service;

import com.mysite.springbootdeveloper.domain.User;
import com.mysite.springbootdeveloper.dto.AddUserRequest;
import com.mysite.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmailAndProvider(String email, String provider) {
        return userRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}