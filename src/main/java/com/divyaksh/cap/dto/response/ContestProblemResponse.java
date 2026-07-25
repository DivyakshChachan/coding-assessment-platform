package com.divyaksh.cap.dto.response;

public record ContestProblemResponse(

        Long id,

        Long problemId,

        String problemTitle,

        String problemSlug

) {}