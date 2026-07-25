package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.ContestRegistrationResponse;
import com.divyaksh.cap.service.ContestRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContestRegistrationController {

    private final ContestRegistrationService registrationService;

    @PostMapping("/contests/{contestId}/register")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ApiResponse<ContestRegistrationResponse>> register(
            @PathVariable Long contestId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        registrationService.register(contestId)
                ));
    }

    @DeleteMapping("/contests/{contestId}/register")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ApiResponse<Void>> unregister(
            @PathVariable Long contestId) {

        registrationService.unregister(contestId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/users/me/registrations")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<ApiResponse<List<ContestRegistrationResponse>>> myRegistrations() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        registrationService.getMyRegistrations()
                )
        );
    }
}