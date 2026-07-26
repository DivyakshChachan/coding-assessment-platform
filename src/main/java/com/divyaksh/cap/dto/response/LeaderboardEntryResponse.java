package com.divyaksh.cap.dto.response;

import lombok.Builder;

@Builder
public record LeaderboardEntryResponse(

        int rank,

        Long userId,

        String name,

        String email,

        int totalScore,

        int solvedProblems

) {}