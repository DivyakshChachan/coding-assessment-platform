package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.request.CreateProblemRequest;
import com.divyaksh.cap.dto.request.UpdateProblemRequest;
import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.ProblemResponse;
import com.divyaksh.cap.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<ProblemResponse> createProblem(
            @Valid @RequestBody CreateProblemRequest request) {

        return ApiResponse.success(
                "Problem created successfully.",
                problemService.createProblem(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProblemResponse> getProblem(@PathVariable Long id) {

        return ApiResponse.success(
                "Problem fetched successfully.",
                problemService.getProblem(id)
        );
    }

    @GetMapping
    public ApiResponse<List<ProblemResponse>> getAllProblems() {

        return ApiResponse.success(
                "Problems fetched successfully.",
                problemService.getAllProblems()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<ProblemResponse> updateProblem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProblemRequest request) {

        return ApiResponse.success(
                "Problem updated successfully.",
                problemService.updateProblem(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<Void> deleteProblem(@PathVariable Long id) {

        problemService.deleteProblem(id);

        return ApiResponse.success("Problem deleted successfully.", null);
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<Void> publishProblem(@PathVariable Long id) {

        problemService.publishProblem(id);

        return ApiResponse.success("Problem published successfully.", null);
    }

    @PatchMapping("/{id}/unpublish")
    @PreAuthorize("hasAnyRole('ADMIN','PROBLEM_SETTER')")
    public ApiResponse<Void> unpublishProblem(@PathVariable Long id) {

        problemService.unpublishProblem(id);

        return ApiResponse.success("Problem unpublished successfully.", null);
    }
}