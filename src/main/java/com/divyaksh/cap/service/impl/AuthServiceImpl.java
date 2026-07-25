package com.divyaksh.cap.service.impl;

import com.divyaksh.cap.dto.request.LoginRequest;
import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.AuthResponse;
import com.divyaksh.cap.dto.response.UserResponse;
import com.divyaksh.cap.entity.Role;
import com.divyaksh.cap.entity.User;
import com.divyaksh.cap.exception.DuplicateResourceException;
import com.divyaksh.cap.exception.UnauthorizedException;
import com.divyaksh.cap.mapper.UserMapper;
import com.divyaksh.cap.repository.UserRepository;
import com.divyaksh.cap.security.CustomUserDetails;
import com.divyaksh.cap.security.JwtTokenProvider;
import com.divyaksh.cap.service.AuthService;
import com.divyaksh.cap.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        User user = userMapper.toEntity(request);
        user.setRole(Role.CANDIDATE);

        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtTokenProvider.generateToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(null)
                .user(userMapper.toResponse(user))
                .build();
    }
    @Override
    public UserResponse getCurrentUser() {

        CustomUserDetails currentUser =
                SecurityUtils.getCurrentUser();

        if (currentUser == null) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        return userMapper.toResponse(currentUser.getUser());
    }
}