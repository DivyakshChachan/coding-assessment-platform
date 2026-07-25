package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.request.CreateTestCaseRequest;
import com.divyaksh.cap.dto.request.UpdateTestCaseRequest;
import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.TestCaseResponse;
import com.divyaksh.cap.service.TestCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/problems/{problemId}/testcases")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<TestCaseResponse>> createTestCase(
            @PathVariable Long problemId,
            @Valid @RequestBody CreateTestCaseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        testCaseService.createTestCase(problemId, request)
                ));
    }

    @GetMapping("/problems/{problemId}/testcases")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<List<TestCaseResponse>>> getTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        testCaseService.getTestCases(problemId)
                )
        );
    }

    @GetMapping("/problems/{problemId}/samples")
    public ResponseEntity<ApiResponse<List<TestCaseResponse>>> getSampleTestCases(
            @PathVariable Long problemId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        testCaseService.getSampleTestCases(problemId)
                )
        );
    }

    @PutMapping("/testcases/{testCaseId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<TestCaseResponse>> updateTestCase(
            @PathVariable Long testCaseId,
            @Valid @RequestBody UpdateTestCaseRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        testCaseService.updateTestCase(testCaseId, request)
                )
        );
    }

    @DeleteMapping("/testcases/{testCaseId}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ResponseEntity<ApiResponse<Void>> deleteTestCase(
            @PathVariable Long testCaseId) {

        testCaseService.deleteTestCase(testCaseId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}