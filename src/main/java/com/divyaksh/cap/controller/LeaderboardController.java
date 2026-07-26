package com.divyaksh.cap.controller;

import com.divyaksh.cap.dto.response.ApiResponse;
import com.divyaksh.cap.dto.response.LeaderboardEntryResponse;
import com.divyaksh.cap.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contests")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/{contestId}/leaderboard")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<LeaderboardEntryResponse>> getLeaderboard(
            @PathVariable Long contestId) {

        return ApiResponse.success(
                leaderboardService.getLeaderboard(contestId)
        );
    }
}