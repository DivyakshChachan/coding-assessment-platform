package com.divyaksh.cap.mapper;

import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.UserResponse;
import com.divyaksh.cap.entity.Role;
import com.divyaksh.cap.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // hashed later in service
                .fullName(request.getFullName())
                .role(Role.CANDIDATE)
                .build();
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}