package com.divyaksh.cap.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/public")
    public String publicApi() {
        return "Public";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminApi() {
        return "Admin";
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/candidate")
    public String candidateApi() {
        return "Candidate";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROBLEM_SETTER')")
    @GetMapping("/setter")
    public String setterApi() {
        return "Setter";
    }
}