package com.divyaksh.cap.dto.response;

import com.divyaksh.cap.constant.Difficulty;
import com.divyaksh.cap.constant.Tag;

import java.time.LocalDateTime;
import java.util.Set;

public record ProblemResponse(

        Long id,

        String title,

        String slug,

        String description,

        String inputFormat,

        String outputFormat,

        String constraints,

        Difficulty difficulty,

        Set<Tag> tags,

        Integer timeLimitMs,

        Integer memoryLimitMb,

        boolean published,

        String createdBy,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {}