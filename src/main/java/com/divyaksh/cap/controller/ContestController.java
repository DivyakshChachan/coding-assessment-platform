package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.request.CreateContestRequest;
import com.divyaksh.cap.dto.request.UpdateContestRequest;
import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.ContestResponse;
import com.divyaksh.cap.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contests")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<ContestResponse>> createContest(
            @Valid @RequestBody CreateContestRequest request) {

        ContestResponse response = contestService.createContest(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{contestId}")
    public ResponseEntity<ApiResponse<ContestResponse>> getContest(
            @PathVariable Long contestId) {

        ContestResponse response = contestService.getContest(contestId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContestResponse>>> getPublishedContests() {

        List<ContestResponse> response = contestService.getPublishedContests();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{contestId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<ContestResponse>> updateContest(
            @PathVariable Long contestId,
            @Valid @RequestBody UpdateContestRequest request) {

        ContestResponse response =
                contestService.updateContest(contestId, request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{contestId}/publish")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<ContestResponse>> publishContest(
            @PathVariable Long contestId) {

        ContestResponse response =
                contestService.publishContest(contestId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{contestId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<ContestResponse>> endContest(
            @PathVariable Long contestId) {

        ContestResponse response =
                contestService.cancelContest(contestId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{contestId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<Void>> deleteContest(
            @PathVariable Long contestId) {

        contestService.deleteContest(contestId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}