package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.ContestProblemResponse;
import com.divyaksh.cap.service.ContestProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contests")
@RequiredArgsConstructor
public class ContestProblemController {

    private final ContestProblemService contestProblemService;

    @PostMapping("/{contestId}/problems/{problemId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<ContestProblemResponse>> addProblem(
            @PathVariable Long contestId,
            @PathVariable Long problemId) {

        ContestProblemResponse response =
                contestProblemService.addProblem(contestId, problemId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @GetMapping("/{contestId}/problems")
    public ResponseEntity<ApiResponse<List<ContestProblemResponse>>> getProblems(
            @PathVariable Long contestId) {

        List<ContestProblemResponse> response =
                contestProblemService.getContestProblems(contestId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{contestId}/problems/{problemId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<Void>> removeProblem(
            @PathVariable Long contestId,
            @PathVariable Long problemId) {

        contestProblemService.removeProblem(contestId, problemId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}