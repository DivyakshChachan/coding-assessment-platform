package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.UserResponse;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.exception.DuplicateResourceException;
import com.divyaksh.cap.mapper.UserMapper;
import com.divyaksh.cap.repository.UserRepository;
import com.divyaksh.cap.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
}