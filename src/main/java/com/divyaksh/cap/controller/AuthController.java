package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.request.LoginRequest;
import com.divyaksh.cap.dto.request.RegisterRequest;
import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.AuthResponse;
import com.divyaksh.cap.dto.response.UserResponse;
import com.divyaksh.cap.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @RequestBody @Valid RegisterRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success("User registered successfully",
                        authService.register(request))
        );
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }
    @RestController
    @RequestMapping("/api/v1/test")
    public class TestController {

        @GetMapping("/hello")
        public String hello() {
            return "JWT Authentication Working!";
        }
    }
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {

        return ResponseEntity.ok(
                ApiResponse.success(authService.getCurrentUser())
        );
    }
}