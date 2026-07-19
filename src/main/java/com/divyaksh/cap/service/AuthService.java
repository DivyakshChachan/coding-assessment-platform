package com.divyaksh.cap.service;

import com.divyaksh.cap.dto.request.LoginRequest;
import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.AuthResponse;
import com.divyaksh.cap.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}