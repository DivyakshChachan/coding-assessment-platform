package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.request.SubmitSolutionRequest;
import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.SubmissionResponse;
import com.divyaksh.cap.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/contests/{contestId}/problems/{problemId}/submit")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<SubmissionResponse> submit(
            @PathVariable Long contestId,
            @PathVariable Long problemId,
            @Valid @RequestBody SubmitSolutionRequest request) {

        return ApiResponse.success(
                submissionService.submit(contestId, problemId, request)
        );
    }

    @GetMapping("/submissions/{submissionId}")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<SubmissionResponse> getSubmission(
            @PathVariable Long submissionId) {

        return ApiResponse.success(
                submissionService.getSubmission(submissionId)
        );
    }

    @GetMapping("/users/me/submissions")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ApiResponse<List<SubmissionResponse>> getMySubmissions() {

        return ApiResponse.success(
                submissionService.getMySubmissions()
        );
    }

    @GetMapping("/contests/{contestId}/submissions")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<List<SubmissionResponse>> getContestSubmissions(
            @PathVariable Long contestId) {

        return ApiResponse.success(
                submissionService.getContestSubmissions(contestId)
        );
    }
}